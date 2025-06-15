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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod

case class LateSubmission(lateSubmissionID: String,
                          incomeSource: Option[String] = None,
                          taxPeriod: Option[String] = None,
                          taxReturnStatus: Option[String] = None,
                          taxPeriodStartDate: Option[String] = None,
                          taxPeriodEndDate: Option[String] = None,
                          taxPeriodDueDate: Option[String] = None,
                          returnReceiptDate: Option[String] = None
                         )

object LateSubmission {

  def create(reportingPeriod: ReportingPeriod,
            returnReceived: Boolean = false,
            addAdditionalIncomeSource: Boolean = false): Seq[LateSubmission] = {
    val lateSubmission1 = LateSubmission(
      lateSubmissionID = "001",
      incomeSource = Some("JB Painting and Decorating"),
      taxReturnStatus = if(returnReceived) Some("Fulfilled") else Some("Open"),
      taxPeriodStartDate = Some(reportingPeriod.taxPeriodStartDate),
      taxPeriodEndDate = Some(reportingPeriod.taxPeriodEndDate),
      taxPeriodDueDate = Some(reportingPeriod.taxPeriodDueDate)
    )
    if(addAdditionalIncomeSource) {
      Seq(lateSubmission1, lateSubmission1.copy(lateSubmissionID = "002",
        incomeSource = Some("UK property rental income")))
    } else { Seq(lateSubmission1)}
  }
  implicit val format: Format[LateSubmission] = Json.format[LateSubmission]
}
