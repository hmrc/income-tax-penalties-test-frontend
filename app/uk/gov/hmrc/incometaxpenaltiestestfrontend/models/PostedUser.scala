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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.models

import play.api.data.Form
import play.api.data.Forms.{boolean, mapping, optional, text}

case class PostedUser(nino: String,
                      userType: Option[String],
                      useBTANavBar: Boolean = false,
                      arn: Option[String] = None
                     ) {

  def isAgent: Boolean = {
    userType.fold(false)(_=="agent")
  }
}

object PostedUser {
    val form: Form[PostedUser] =
      Form(
        mapping(
          "nino" -> text,
          "userType" -> optional(text),
          "useBTANavBar" -> boolean,
          "arn" -> optional(text),
        )(PostedUser.apply)(PostedUser.unapply)
      )
}