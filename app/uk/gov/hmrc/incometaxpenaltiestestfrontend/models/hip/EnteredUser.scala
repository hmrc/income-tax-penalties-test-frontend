/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip

import play.api.data.Form
import play.api.data.Forms._

case class EnteredUser(nino: String,
                       utr: String,
                       mtdItId: Option[String],
                       userType: Option[String],
                       arn: Option[String] = None
                     ) {

  def isPrimaryAgent: Boolean = {
    userType.fold(false)(_ == "primary-agent")
  }

  def isSecondaryAgent: Boolean = {
    userType.fold(false)(_ == "secondary-agent")
  }
}

object EnteredUser {
    val form: Form[EnteredUser] =
      Form(
        mapping(
          "nino" -> nonEmptyText,
          "utr" -> nonEmptyText,
          "mtdItId" -> optional(text),
          "userType" -> optional(text),
          "arn" -> optional(text)
        )(EnteredUser.apply)(o => Some(Tuple.fromProductTyped(o)))
      )
}