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

import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.RadioItem
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.UserData
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.TextValueForm
import uk.gov.hmrc.incometaxpenaltiestestfrontend.views.html.FilterPenaltiesPage
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}

@Singleton
class FilterPenaltiesController @Inject()(
  mcc: MessagesControllerComponents,
  filterPenaltiespage: FilterPenaltiesPage)
    extends FrontendController(mcc) {

  lazy val form = TextValueForm.form

  val filterPenaltiesType: Action[AnyContent] = Action { implicit request =>
    val radioItems = createRadioItems(false)
    val postAction = routes.FilterPenaltiesController.submitFilterPenaltiesType
    Ok(filterPenaltiespage(form, postAction, radioItems, "Filter by Penalty Type"))
  }

  val filterByLSP: Action[AnyContent] = Action { implicit request =>
    val radioItems = createRadioItems(true)
    val postAction = routes.FilterPenaltiesController.submitFilterByLSP
      Ok(filterPenaltiespage(form, postAction, radioItems, "Filter by number of Late Submission Penalties"))
  }

  val submitFilterPenaltiesType: Action[AnyContent] = Action { implicit request =>
    form.bindFromRequest().fold(_ => {
      Redirect(routes.CustomLoginController.showLogin)
    }, penaltyType =>
      if(penaltyType == "LSP") {
        Redirect(routes.FilterPenaltiesController.filterByLSP)
      } else {
        Redirect(routes.CustomLoginController.showFilteredLogin(penaltyType, None))
      }
    )
  }

  val submitFilterByLSP: Action[AnyContent] = Action { implicit request =>
    form.bindFromRequest().fold(_ => {
      Redirect(routes.CustomLoginController.showLogin)
    }, lspNum =>
      Redirect(routes.CustomLoginController.showFilteredLogin("LSP", Some(lspNum)))
    )
  }

  def createRadioItems(isNumOfLSP: Boolean): Seq[RadioItem] = {
    val items = if(isNumOfLSP) {
      UserData.lspNumsToDataMap.keys.toSeq.sorted
    } else {
      UserData.penaltyTypesToDataMap.keys.toSeq.sorted
    }

    items.map{item =>
      RadioItem(
        content = Text(item),
        value = Some(item)
      )
    }
  }

}
