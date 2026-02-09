/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

///*
// * Copyright 2023 HM Revenue & Customs
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */

package uk.gov.hmrc.incometaxpenaltiestestfrontend.controllers

import play.api.i18n.I18nSupport
import play.api.mvc.*
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.incometaxpenaltiestestfrontend.config.AppConfig
import uk.gov.hmrc.incometaxpenaltiestestfrontend.connectors.{CustomAuthConnector, TimeMachineConnector}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.UserData
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.EnteredUser
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.{PostedUser, UserRecord}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.views.html.*
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CustomLoginController @Inject()(
                                       loginPage: LoginPage,
                                       enterUserPage: EnterUserPage,
                                       val customAuthConnector: CustomAuthConnector,
                                       val timeMachineConnector: TimeMachineConnector
                                     )(implicit val appConfig: AppConfig,
                                       mcc: MessagesControllerComponents,
                                       executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport {

  lazy val displayQAUsers = appConfig.displayQAUserRecords
  lazy val allUserRecords = UserData.allUserRecords(displayQAUsers)
  // Logging page functionality
  val showLogin: Action[AnyContent] = Action { implicit request =>
    Ok(loginPage(routes.CustomLoginController.postLogin, allUserRecords, false, displayQAUsers))
  }

  def showFilteredLogin(penaltyType: String, optLspNum: Option[String]) = Action { implicit request: MessagesRequest[AnyContent] =>
    val filteredUserData = UserData.getUserRecordsForPenaltyType(penaltyType, optLspNum)
    Ok(loginPage(routes.CustomLoginController.postLogin, filteredUserData, true, false))
  }

  val showEnterUser: Action[AnyContent] = Action { implicit request =>
    Ok(enterUserPage(EnteredUser.form, routes.CustomLoginController.postEnteredUser))
  }

  val postLogin: Action[AnyContent] = Action.async { implicit request =>
    PostedUser.form.bindFromRequest().fold(
      formWithErrors =>
        Future(BadRequest(s"Invalid form submission: $formWithErrors")),
      (postedUser: PostedUser) => {
        val user = allUserRecords(postedUser.nino)
        loginInUser(user, postedUser.isAgent, postedUser.useBTANavBar, postedUser.arn)
      }
    )
  }

  val postEnteredUser: Action[AnyContent] = Action.async { implicit request =>
    EnteredUser.form.bindFromRequest().fold(
      formWithErrors => {
        Future(BadRequest(enterUserPage(formWithErrors, routes.CustomLoginController.postEnteredUser)))
      },
      (enteredUser: EnteredUser) => {
        val user = allUserRecords.get(enteredUser.nino).collect { case (x) if x.utr == enteredUser.utr => x }
          .getOrElse(UserRecord(enteredUser.nino, enteredUser.mtdItId.getOrElse("10000"),
            enteredUser.utr, "entered user", "ignore"))
        loginInUser(user, enteredUser.isAgent, false, enteredUser.arn)
      }
    )
  }

  private def loginInUser(user: UserRecord, isAgent: Boolean, useBTANavBar: Boolean, arn: Option[String])
                         (implicit hc: HeaderCarrier): Future[Result] = {
    val loggedInUser = for {
      login <- customAuthConnector.login(user, isAgent, arn)
      _ <- updateTimeMachine(user)
    } yield login

    loggedInUser.map {
      case (authExchange, _) =>
        val (bearer, auth) = (authExchange.bearerToken, authExchange.sessionAuthorityUri)
        if (isAgent) {
          val redirectUrl = routes.SetupAgentController.addAgentData(user.nino, user.utr, Option(user.mtditid)).url
          successRedirect(bearer, auth, redirectUrl, None)
        } else {
          val origin = if (useBTANavBar) "BTA" else "PTA"
          val redirectUrl = appConfig.penaltiesHomeUrl
          successRedirect(bearer, auth, redirectUrl, Some(origin))
        }
      case null =>
        InternalServerError("something went wrong..")
    }
  }

  private def successRedirect(bearer: String, auth: String, redirectUrl: String, origin: Option[String]): Result = {
    val ggSession = SessionBuilder.buildGGSession(AuthExchange(bearerToken = bearer,
      sessionAuthorityUri = auth))
    val ggSessionWithOrigin = SessionBuilder.addOriginToSession(origin, ggSession)
    Redirect(redirectUrl)
      .withSession(ggSessionWithOrigin)
  }

  private def updateTimeMachine(user: UserRecord)(implicit hc: HeaderCarrier): Future[Unit] = {
    user.timeMachineDate match {
      case "ignore" => Future.unit
      case "now" =>
        for {
          _ <- timeMachineConnector.updatePenaltiesDownstream(None)
          _ <- timeMachineConnector.updateITSAPenalties(None)
          _ <- timeMachineConnector.updateITSAPenaltiesAppeals(None)
        } yield ()
      case date =>
        val formattedDate = LocalDate.parse(date.replace("/", "-"), DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        for {
          _ <- timeMachineConnector.updatePenaltiesDownstream(Some(formattedDate))
          _ <- timeMachineConnector.updateITSAPenalties(Some(date))
          _ <- timeMachineConnector.updateITSAPenaltiesAppeals(Some(date))
        } yield ()
    }
  }

}
