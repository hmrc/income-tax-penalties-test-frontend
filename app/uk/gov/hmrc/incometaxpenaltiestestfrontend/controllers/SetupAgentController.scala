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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.controllers

import javax.inject.{Inject, Singleton}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.connectors.SessionDataConnector
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.config.AppConfig
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.UserData
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.SessionDataModel
import uk.gov.hmrc.incometaxpenaltiestestfrontend.config.ErrorHandler
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.SessionDataPostResponse.SessionDataPostSuccess
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import java.time.format.DateTimeFormatter._
import java.time.LocalDate
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SetupAgentController @Inject()(
                                      sessionDataConnector: SessionDataConnector,
                                      mcc: MessagesControllerComponents,
                                      appConfig: AppConfig,
                                      errorHandler: ErrorHandler,
                                      implicit val ec: ExecutionContext
                                    ) extends FrontendController(mcc) {

  lazy val displayQAUsers = appConfig.displayQAUserRecords
  lazy val allUserRecords = UserData.allUserRecords(displayQAUsers)

  def addAgentData(nino: String): Action[AnyContent] = Action.async { implicit req =>

    val userRecord = allUserRecords(nino)
    val sessionData = new SessionDataModel(userRecord)


    sessionDataConnector.postSessionData(sessionData).flatMap {
      case Right(value: SessionDataPostSuccess) =>
        val session = req.session + ("pocAchievementDate" -> LocalDate.now().format(ISO_DATE)) + ("ClientMTDID" -> userRecord.mtditid) + ("Nino" -> userRecord.nino)
        Future(Redirect(appConfig.penaltiesAgentHomeUrl).withSession(session))
      case _ => errorHandler.showInternalServerError()
    }
  }
}
