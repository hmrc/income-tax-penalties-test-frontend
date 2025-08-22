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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LSP, LSPSummary}

object AB411145A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 4,
    pocAchievementDate = Some("2028-11-07")
  )

  val lspPenalty1a = LateSubmissionPenaltyDetails.cancelledLateSubmissionPenalty(
      ReportingPeriod(2028, Some(0)),
      addAdditionalIncomeSource = true)
    .withPenaltyCategory("T")

  val lspPenalty1b = LateSubmissionPenaltyDetails.cancelledLateSubmissionPenalty(
      ReportingPeriod(2027, Some(3)),
      addAdditionalIncomeSource = true,
      appealLevel = "Tribunal")
    .withPenaltyCategory("T")


  val lspPenalty1c = LateSubmissionPenaltyDetails.cancelledLateSubmissionPenalty(
      ReportingPeriod(2027, None),
      addAdditionalIncomeSource = true)
    .withPenaltyCategory("T")


  val lspPenalty2 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(2)),
    penaltyOrder = "3",
    returnSubmitted = true,
    addAdditionalIncomeSource = true)


  val lspPenalty3 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(1)),
    penaltyOrder = "2",
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  val lspPenalty4 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(0)),
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1a, lspPenalty1b, lspPenalty1c,
      lspPenalty2, lspPenalty3, lspPenalty4)
  ))

  override def optComplianceData: Option[CompliancePayload] = Some(
    CompliancePayload.apply(nino)
      .withObligationDetail(ReportingPeriod(2028, Some(2)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(1)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(0)), false)
      .withObligationDetail(ReportingPeriod(2027, Some(3)), false)
      .withObligationDetail(ReportingPeriod(2027, Some(2)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(1)), true)
      .withObligationDetail(ReportingPeriod(2027, None), true)
      .withObligationDetail(ReportingPeriod(2027, Some(0)), true)
  )
  override val nino: String = "AB411145A"
  override val description: String = "LSP4, 3 points with 3 success appeals (1 each level) when would be 4th point"
  override val timemachineDate: String = "01/06/2028"
  override val mtdItId: String = "XTIT12345411145A"
  override val utr: String = "0000411145A"
}
