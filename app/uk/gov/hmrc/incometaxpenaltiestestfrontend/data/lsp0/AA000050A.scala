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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp0
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.{LateSubmissionPenaltyDetails, UserDetailsData}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.complianceData.CompliancePayload
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LSP, LSPSummary}

object AA000050A extends UserDetailsData {

  val lspSummary = LSPSummary(
    inactivePenaltyPoints = 5,
    pocAchievementDate = Some("2027-01-31")
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.expired(
    ReportingPeriod(2026, Some(0)),
    addAdditionalIncomeSource = true)

  val lspPenalty2 = LateSubmissionPenaltyDetails.expired(
    ReportingPeriod(2026, Some(1)),
    penaltyOrder = "2",
    addAdditionalIncomeSource = true)

  val lspPenalty3 = LateSubmissionPenaltyDetails.expired(
    ReportingPeriod(2026, Some(2)),
    penaltyOrder = "3",
    addAdditionalIncomeSource = true)

  val lspPenalty4 = LateSubmissionPenaltyDetails.expired(
    ReportingPeriod(2026, None),
    penaltyOrder = "4",
    addAdditionalIncomeSource = true)

  val lspPenalty5 = LateSubmissionPenaltyDetails.expired(
    ReportingPeriod(2026, Some(3)),
    penaltyOrder = "5",
    addAdditionalIncomeSource = true)

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1, lspPenalty2, lspPenalty3, lspPenalty4, lspPenalty5)
  ))

  override def optComplianceData: Option[CompliancePayload] = Some(
    CompliancePayload.apply(nino)
      .withObligationDetail(ReportingPeriod(2026, None), true)
      .withObligationDetail(ReportingPeriod(2026, Some(3)), true)
      .withObligationDetail(ReportingPeriod(2026, Some(2)), true)
      .withObligationDetail(ReportingPeriod(2026, Some(1)), true)
      .withObligationDetail(ReportingPeriod(2026, Some(0)), true)
  )
  override val nino: String = "AA000050A"
  override val mtdItId: String = "000050"
  override val utr: String = "1234000050"
  override val description: String = "0 LSP Update- Compliance period ended and points expired"
  override val timemachineDate: String = "01/08/2027"
}
