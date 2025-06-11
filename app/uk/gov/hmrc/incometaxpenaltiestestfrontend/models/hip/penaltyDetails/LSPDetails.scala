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

case class LSPDetails(penaltyNumber: String,
                      penaltyCreationDate: String,
                      penaltyExpiryDate: String,
                      triggeringProcess: String = "ICRU",
                      penaltyOrder: Option[String] = None,
                      penaltyCategory: Option[String] = Some("P"),
                      penaltyStatus: Option[String] = None,
                      fapIndicator: Option[String] = None,
                      expiryReason: Option[String] = None,
                      communicationsDate: Option[String] = None,
                      lateSubmissions: Option[Seq[LateSubmission]] = None,
                      appealInformation: Option[Seq[AppealInformation]] = None,
                      chargeReference: Option[String] = None,
                      chargeAmount: Option[BigDecimal] = None,
                      chargeOutstandingAmount: Option[BigDecimal] = None,
                      chargeDueDate: Option[String] = None
                     ) {
  def withLateSubmission(submissions: Seq[LateSubmission]): LSPDetails =
    copy(lateSubmissions = Some(submissions))

  def withChargeAmount(amount: BigDecimal): LSPDetails =
    copy(chargeAmount = Some(amount))

  def withChargeOutstandingAmount(amount: BigDecimal): LSPDetails =
    copy(chargeOutstandingAmount = Some(amount))

  def withAppealInformation(appealInfo: AppealInformation): LSPDetails =
    copy(appealInformation = Some(Seq(appealInfo)))

  def withPenaltyCategory(category: String): LSPDetails =
    copy(penaltyCategory = Some(category))

  def withAdjustment(): LSPDetails = {
    copy(fapIndicator = Some("X"))
  }

  def withExpiryReason(reason: String): LSPDetails =
    copy(expiryReason = Some(reason))
}

object LSPDetails {
  def create(reportingPeriod: ReportingPeriod,
            penaltyOrder: String = "1",
            penaltyStatus: Option[String] = Some("Active")): LSPDetails = {
    val penaltyNumber = "00500000032" + penaltyOrder
    LSPDetails(
      penaltyNumber = penaltyNumber,
      penaltyCreationDate = reportingPeriod.taxPeriodDueDate,
      penaltyExpiryDate = reportingPeriod.penaltyExpiryDate,
      penaltyOrder = Some(penaltyOrder),
      penaltyStatus = penaltyStatus,
      communicationsDate = Some(reportingPeriod.taxPeriodDueDate)
    )
  }

  implicit val format: Format[LSPDetails] = Json.format[LSPDetails]
}
