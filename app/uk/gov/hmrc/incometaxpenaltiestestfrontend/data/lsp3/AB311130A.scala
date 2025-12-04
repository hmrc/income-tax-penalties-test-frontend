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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp3

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.{LateSubmissionPenaltyDetails, UserDetailsData}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.complianceData.CompliancePayload
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{AppealInformation, LSP, LSPSummary}

object AB311130A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 2,
    regimeThreshold = 2,
    pocAchievementDate = Some("2029-02-28")
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.dueOrOverdue(
    ReportingPeriod(2027, None),
    penaltyOrder = "3",
    addAdditionalIncomeSource = true).withAppealInformation(
    AppealInformation.create("Rejected", "First")
  ).withPenaltyCategory("C")

  val lspPenalty2 = LateSubmissionPenaltyDetails.paid(
    ReportingPeriod(2026, None),
      penaltyOrder = "2",
    addAdditionalIncomeSource = true
  ).withPenaltyCategory("T")

  val lspPenalty3 = LateSubmissionPenaltyDetails.active(
      ReportingPeriod(2025, None),
      returnSubmitted = true,
      addAdditionalIncomeSource = true)
    .withAppealInformation(
      AppealInformation.create("UnderAppeal", "First")
    )

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1, lspPenalty2, lspPenalty3)
  ))

  override def optComplianceData: Option[CompliancePayload] = Some(
    CompliancePayload.apply(nino)
      .withObligationDetail(ReportingPeriod(2028, None), false)
      .withObligationDetail(ReportingPeriod(2027, None), false)
      .withObligationDetail(ReportingPeriod(2026, None), true)
      .withObligationDetail(ReportingPeriod(2025, None), true)
  )
  override val nino: String = "AB311130A"
  override val description: String = "LSP2 Return - threshold reached, appeal at tribunal"
  override val timemachineDate: String = "28/02/2028"
  override val mtdItId: String = "311130"
  override val utr: String = "0000311130"
}
