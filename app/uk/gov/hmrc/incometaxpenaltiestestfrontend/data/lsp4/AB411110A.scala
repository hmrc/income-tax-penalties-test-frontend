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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp4

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.{LateSubmissionPenaltyDetails, UserDetailsData}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.complianceData.CompliancePayload
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LSP, LSPDetails, LSPSummary}

object AB411110A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 4,
    pocAchievementDate = Some("2028-11-07")
  )

  val lspPenalty1: LSPDetails = LateSubmissionPenaltyDetails.paid(
      ReportingPeriod(2027, Some(2)),
      penaltyOrder = "4",
      addAdditionalIncomeSource = true)
    .withPenaltyCategory("T")
    .withPenaltyNumber("005000001043")

  val lspPenalty2: LSPDetails = LateSubmissionPenaltyDetails.cancelledLateSubmissionPenalty(
      ReportingPeriod(2027, Some(1)),
      appealLevel = "Second",
      addAdditionalIncomeSource = true)
    .withPenaltyNumber("005000001021")

  val lspPenalty3: LSPDetails = LateSubmissionPenaltyDetails.active(
      ReportingPeriod(2027, Some(0)),
      penaltyOrder = "3",
      addAdditionalIncomeSource = true)
    .withPenaltyNumber("005000001016")

  val lspPenalty4: LSPDetails = LateSubmissionPenaltyDetails.active(
      ReportingPeriod(2026, Some(3)),
      penaltyOrder = "2",
      returnSubmitted = true,
      addAdditionalIncomeSource = true)
    .withPenaltyNumber("005000001018")

  val lspPenalty5: LSPDetails = LateSubmissionPenaltyDetails.active(
      ReportingPeriod(2026, None),
      returnSubmitted = true,
      addAdditionalIncomeSource = true)
    .withPenaltyNumber("005000001028")

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1, lspPenalty2, lspPenalty3, lspPenalty4, lspPenalty5)
  ))

  override def optComplianceData: Option[CompliancePayload] = Some(
    CompliancePayload.apply(nino)
      .withObligationDetail(ReportingPeriod(2028, Some(2)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(1)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(0)), false)
      .withObligationDetail(ReportingPeriod(2027, Some(3)), false)
      .withObligationDetail(ReportingPeriod(2027, None), false)
      .withObligationDetail(ReportingPeriod(2027, Some(2)), false)
      .withObligationDetail(ReportingPeriod(2027, Some(1)), false)
      .withObligationDetail(ReportingPeriod(2027, Some(0)), false)
      .withObligationDetail(ReportingPeriod(2026, Some(3)), true)
      .withObligationDetail(ReportingPeriod(2026, None), true)
  )

  override val nino: String = "AB411110A"
  override val mtdItId: String = "411110"
  override val utr: String = "0000411110"
  override val description: String = "LSP4 - paid and success appeal penalty (second stage) for LSP 4"
  override val timemachineDate: String = "01/12/2027"
}
