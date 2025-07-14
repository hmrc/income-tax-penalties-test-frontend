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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp5

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.{LateSubmissionPenaltyDetails, UserDetailsData}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.complianceData.CompliancePayload
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{AppealInformation, LSP, LSPSummary}

object AB511130A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 5,
    pocAchievementDate = Some("2028-11-30")
  )
  val lspPenalty1a = LateSubmissionPenaltyDetails.cancelledLateSubmissionPenalty(
    ReportingPeriod(2028, Some(0)),
    addAdditionalIncomeSource = true,
      penaltyOrder = "8",
    appealLevel = "Tribunal")
    .withPenaltyCategory("T")

  val lspPenalty1b = LateSubmissionPenaltyDetails.cancelledLateSubmissionPenalty(
    ReportingPeriod(2027, Some(3)),
    penaltyOrder = "7",
    addAdditionalIncomeSource = true,
    appealLevel = "Second")
    .withPenaltyCategory("T")

  val lspPenalty1c = LateSubmissionPenaltyDetails.cancelledLateSubmissionPenalty(
      ReportingPeriod(2027, None),
    penaltyOrder = "6",
      addAdditionalIncomeSource = true)
    .withPenaltyCategory("T")

  val lspPenalty2 = LateSubmissionPenaltyDetails.paid(
      ReportingPeriod(2027, Some(2)),
      penaltyOrder = "5",
      addAdditionalIncomeSource = true)
    .withPenaltyCategory("T")
    .withAppealInformation(
      AppealInformation.create("Rejected", "Tribunal")
    )

  val lspPenalty3 = LateSubmissionPenaltyDetails.paid(
    ReportingPeriod(2027, Some(1)),
    penaltyOrder = "4",
    addAdditionalIncomeSource = true)
    .withPenaltyCategory("T")
    .withAppealInformation(
    AppealInformation.create("Rejected", "Tribunal")
  )

  val lspPenalty4 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(0)),
    penaltyOrder = "3",
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  val lspPenalty5 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2026, None),
    penaltyOrder = "2",
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  val lspPenalty6 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2026, Some(3)),
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1a, lspPenalty1b, lspPenalty1c,
      lspPenalty3, lspPenalty4, lspPenalty5,
      lspPenalty6)
  ))

  override def optComplianceData: Option[CompliancePayload] = Some(
    CompliancePayload.apply(nino)
      .withObligationDetail(ReportingPeriod(2028, Some(2)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(1)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(0)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(3)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(2)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(1)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(0)), true)
      .withObligationDetail(ReportingPeriod(2026, Some(3)), true)
      .withObligationDetail(ReportingPeriod(2026, None), true)
  )
  override val nino: String = "AB511130A"
  override val description: String = "LSP5, 5 points (2 rejected tribunal) and 3 success appeals (1 each level) "
  override val timemachineDate: String = "01/06/2028"
  override val mtdItId: String = "XTIT12345511130"
  override val utr: String = "0000511130"
}
