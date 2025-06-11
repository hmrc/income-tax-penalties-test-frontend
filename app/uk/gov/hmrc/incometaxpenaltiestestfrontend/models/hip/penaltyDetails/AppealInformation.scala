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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails

import play.api.libs.json.{Format, Json}

case class AppealInformation(appealStatus: String,
                             appealLevel: Option[String],
                             appealDescription: String = "some description")

object AppealInformation {
  def create(status: String, appealLevel: String): AppealInformation = {
    AppealInformation(
      getAppealStatusCode(status),
      Some(getAppealLevelCode(appealLevel))
    )
  }

  def getAppealStatusCode(status: String): String = status match {
    case "UnderAppeal" => "A"
    case "Upheld" => "B"
    case "Rejected" => "C"
    case _ => "99"
  }

  def getAppealLevelCode(level: String): String = level match {
    case "First" => "01"
    case "Second" => "02"
    case _ => "03"
  }
  implicit val format: Format[AppealInformation] = Json.format[AppealInformation]
}


