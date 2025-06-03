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
import play.api.mvc._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.config.AppConfig
import uk.gov.hmrc.incometaxpenaltiestestfrontend.connectors.CustomAuthConnector
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.UserData
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.PostedUser
import uk.gov.hmrc.incometaxpenaltiestestfrontend.views.html.LoginPage
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CustomLoginController @Inject()(implicit val appConfig: AppConfig,
                                      implicit val mcc: MessagesControllerComponents,
                                      implicit val executionContext: ExecutionContext,
                                      loginPage: LoginPage,
                                      val customAuthConnector: CustomAuthConnector
                                     ) extends FrontendController(mcc) with I18nSupport {

  lazy val userData = UserData.allUserRecords.values.toSeq.sortBy(_.nino)

  // Logging page functionality
  val showLogin: Action[AnyContent] = Action { implicit request =>
    Ok(loginPage(routes.CustomLoginController.postLogin, userData))
  }

  val postLogin: Action[AnyContent] = Action.async { implicit request =>
    PostedUser.form.bindFromRequest().fold(
      formWithErrors =>
        Future(BadRequest(s"Invalid form submission: $formWithErrors")),
      (postedUser: PostedUser) => {
        customAuthConnector.login(postedUser.nino, postedUser.isAgent).map {
          case (authExchange, _) =>
            val (bearer, auth) = (authExchange.bearerToken, authExchange.sessionAuthorityUri)
            if (postedUser.isAgent) {
              val redirectUrl = routes.SetupAgentController.addAgentData(postedUser.nino).url
              successRedirect(bearer, auth, redirectUrl, None)
            } else {
              val origin = if (postedUser.useBTANavBar) "BTA" else "PTA"
              val redirectUrl = appConfig.penaltiesHomeUrl
              successRedirect(bearer, auth, redirectUrl, Some(origin))
            }
          case code =>
            InternalServerError("something went wrong.." + code)
        }
      }
    )
  }

  private def successRedirect(bearer: String, auth: String, redirectUrl: String, origin: Option[String]): Result = {
    val ggSession = SessionBuilder.buildGGSession(AuthExchange(bearerToken = bearer,
      sessionAuthorityUri = auth))
    val ggSessionWithOrigin = SessionBuilder.addOriginToSession(origin, ggSession)
    Redirect(redirectUrl)
      .withSession(ggSessionWithOrigin)
  }

}
