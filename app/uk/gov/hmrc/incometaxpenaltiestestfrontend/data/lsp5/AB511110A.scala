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

object AB511110A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 4,
    pocAchievementDate = Some("2028-05-07")
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.paid(
    ReportingPeriod(2027, Some(0)),
      penaltyOrder = "5",
    addAdditionalIncomeSource = true)
    .withPenaltyCategory("T")
    .withAppealInformation(
      AppealInformation.create("Rejected", "First")
    )

  val lspPenalty2 = LateSubmissionPenaltyDetails.paid(
    ReportingPeriod(2026, Some(3)),
    penaltyOrder = "4",
    addAdditionalIncomeSource = true)
    .withPenaltyCategory("T")
    .withAppealInformation(
    AppealInformation.create("Rejected", "Tribunal")
  )

  val lspPenalty3 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2026, None),
    penaltyOrder = "3",
    returnSubmitted = true,
    addAdditionalIncomeSource = true)
    .withAppealInformation(
    AppealInformation.create("UnderAppeal", "Tribunal")
  )

  val lspPenalty4 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2026, Some(2)),
    penaltyOrder = "2",
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  val lspPenalty5 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2026, Some(1)),
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1, lspPenalty2, lspPenalty3, lspPenalty4, lspPenalty5)
  ))

  override def optComplianceData: Option[CompliancePayload] = Some(
    CompliancePayload.apply(nino)
      .withObligationDetail(ReportingPeriod(2028, Some(0)), false)
      .withObligationDetail(ReportingPeriod(2027, Some(3)), false)
      .withObligationDetail(ReportingPeriod(2027, None), false)
      .withObligationDetail(ReportingPeriod(2027, Some(2)), false)
      .withObligationDetail(ReportingPeriod(2027, Some(1)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(0)), false)
      .withObligationDetail(ReportingPeriod(2026, Some(3)), true)
      .withObligationDetail(ReportingPeriod(2026, None), true)
      .withObligationDetail(ReportingPeriod(2026, Some(2)), true)
      .withObligationDetail(ReportingPeriod(2026, Some(1)), true)
  )
  override val nino: String = "AB511110A"
  override val description: String = "LSP5- penalties paid, latest penalty appeal rejected, penalty 4 tribunal rejected, penalty 1-3 tribunal under appeal"
  override val timemachineDate: String = "30/05/2027"
  override val mtdItId: String = "XTIT12345511110"
  override val utr: String = "0000511110"
}
