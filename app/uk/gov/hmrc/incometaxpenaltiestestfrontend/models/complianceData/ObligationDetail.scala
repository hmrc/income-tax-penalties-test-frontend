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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.models.complianceData

import play.api.libs.json.{Json, OFormat}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod

case class ObligationDetail(
                             status: String, // either O for open or F for fullfilled,
                             inboundCorrespondenceFromDate: String,
                             inboundCorrespondenceToDate: String,
                             inboundCorrespondenceDateReceived: Option[String],
                             inboundCorrespondenceDueDate: String,
                             periodKey: String = "#001"
                           )

object ObligationDetail {
  def apply(reportingPeriod: ReportingPeriod, isFullfilled: Boolean): ObligationDetail = {
    ObligationDetail(
      status = if(isFullfilled) "F" else "O",
      inboundCorrespondenceFromDate = reportingPeriod.taxPeriodStartDate,
      inboundCorrespondenceToDate = reportingPeriod.taxPeriodEndDate,
      inboundCorrespondenceDueDate = reportingPeriod.taxPeriodDueDate,
      inboundCorrespondenceDateReceived = if(isFullfilled) Some(reportingPeriod.defaultRecievedDate) else None
    )
  }
  implicit val format: OFormat[ObligationDetail] = Json.format[ObligationDetail]
}
