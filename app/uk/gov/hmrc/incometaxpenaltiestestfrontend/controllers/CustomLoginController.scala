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
//
//package uk.gov.hmrc.incometaxpenaltiestestfrontend.controllers
//
//import play.api.Logger
//import play.api.i18n.I18nSupport
//import play.api.mvc._
//import uk.gov.hmrc.http.HeaderCarrier
//import uk.gov.hmrc.incometaxpenaltiestestfrontend.config.AppConfig
//import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.PostedUser
//import uk.gov.hmrc.incometaxpenaltiestestfrontend.views.html.LoginPage
//
//import javax.inject.{Inject, Singleton}
//import scala.concurrent.{ExecutionContext, Future}
//
//@Singleton
//class CustomLoginController @Inject()(implicit val appConfig: AppConfig,
//                                      implicit val mcc: MessagesControllerComponents,
//                                      implicit val executionContext: ExecutionContext,
//                                      loginPage: LoginPage,
//                                      val customAuthConnector: CustomAuthConnector
//                                     ) extends BaseController with I18nSupport {
//
//  // Logging page functionality
//  val showLogin: Action[AnyContent] = Action.async { implicit request =>
//    userRepository.findAll().map(userRecords =>
//      Ok(loginPage(routes.CustomLoginController.postLogin, userRecords, testOnlyAppConfig.optOutUserPrefixes))
//    )
//  }
//
//  val postLogin: Action[AnyContent] = Action.async { implicit request =>
//    PostedUser.form.bindFromRequest().fold(
//      formWithErrors =>
//        Future.successful(BadRequest(s"Invalid form submission: $formWithErrors")),
//      (postedUser: PostedUser) => {
//
//        userRepository.findUser(postedUser.nino).flatMap(
//          user =>
//            customAuthConnector.login(user.nino, postedUser.isAgent, postedUser.isSupporting).flatMap {
//              case (authExchange, _) =>
//                val (bearer, auth) = (authExchange.bearerToken, authExchange.sessionAuthorityUri)
//                val redirectURL = if (postedUser.isAgent)
//                  s"report-quarterly/income-and-expenses/view/test-only/stub-client/nino/${user.nino}/utr/" + user.utr
//                else {
//                  val origin = if(postedUser.usePTANavBar) "PTA" else "BTA"
//                  s"report-quarterly/income-and-expenses/view?origin=$origin"
//                }
//                val homePage = s"${appConfig.itvcFrontendEnvironment}/$redirectURL"
//                Future.successful(successRedirect(bearer, auth, homePage))
//
//              case code =>
//                Future.successful(InternalServerError("something went wrong.." + code))
//            }
//        )
//      }
//    )
//  }
//
//  private def successRedirect(bearer: String, auth: String, homePage: String): Result = {
//    Redirect(homePage)
//      .withSession(
//        SessionBuilder.buildGGSession(AuthExchange(bearerToken = bearer,
//          sessionAuthorityUri = auth)))
//  }
//
//}
