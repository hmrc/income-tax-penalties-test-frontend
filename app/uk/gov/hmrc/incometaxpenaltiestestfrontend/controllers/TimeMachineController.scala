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

import play.api.i18n.I18nSupport
import play.api.mvc.*
import uk.gov.hmrc.http.InternalServerException
import uk.gov.hmrc.incometaxpenaltiestestfrontend.config.AppConfig
import uk.gov.hmrc.incometaxpenaltiestestfrontend.connectors.TimeMachineConnector
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.{TimeMachineData, TimeMachineForm}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.views.html.TimeMachine
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TimeMachineController @Inject()(timeMachinePage: TimeMachine,
                                      val timeMachineConnector: TimeMachineConnector
                                     )(implicit val appConfig: AppConfig,
                                       mcc: MessagesControllerComponents,
                                       executionContext: ExecutionContext) extends FrontendController(mcc) with I18nSupport:

  val onPageLoad: Action[AnyContent] = Action:
    implicit request =>
      Ok(timeMachinePage(TimeMachineForm.form, routes.TimeMachineController.submit))


  val reset: Action[AnyContent] = Action.async:
    implicit request =>
      for
        _ <- timeMachineConnector.updateITSAPenalties(None)
        _ <- timeMachineConnector.updateITSAPenaltiesAppeals(None)
      yield
        Redirect(routes.CustomLoginController.showLogin)

  val resetDownstream: Action[AnyContent] = Action.async:
    implicit request =>
      timeMachineConnector.updatePenaltiesDownstream(None).map(_ => Redirect(routes.CustomLoginController.showLogin))

  val submit: Action[AnyContent] = Action.async:
    implicit request =>
      TimeMachineForm.form.bindFromRequest().fold(
        formWithErrors => Future.successful(BadRequest(timeMachinePage(formWithErrors, routes.TimeMachineController.submit))),
        {
          case TimeMachineData(true, false, dateInput) =>
            timeMachineConnector.updatePenaltiesDownstream(Some(dateInput))
              .map(_ => Redirect(routes.CustomLoginController.showLogin))

          case TimeMachineData(true, true, dateInput) =>
            for
              _ <- timeMachineConnector.updatePenaltiesDownstream(Some(dateInput))
              _ <- timeMachineConnector.updateITSAPenalties(Some(dateInput))
              _ <- timeMachineConnector.updateITSAPenaltiesAppeals(Some(dateInput))
            yield Redirect(routes.CustomLoginController.showLogin)

          case TimeMachineData(false, true, dateInput) =>
            for
              _ <- timeMachineConnector.updateITSAPenalties(Some(dateInput))
              _ <- timeMachineConnector.updateITSAPenaltiesAppeals(Some(dateInput))
            yield Redirect(routes.CustomLoginController.showLogin)

          case _ =>
            throw new InternalServerException("[TimeMachineController][submit] User chose an invalid combination")
        }
      )

