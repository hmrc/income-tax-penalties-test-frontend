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

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp4.AA400000A.nino
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.{LateSubmissionPenaltyDetails, UserDetailsData}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.complianceData.CompliancePayload
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{AppealInformation, LSP, LSPSummary}

object AA411110A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 3
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.dueOrOverdue(
    ReportingPeriod(2027, None),
      penaltyOrder = "4",
    addAdditionalIncomeSource = true)
    .withPenaltyCategory("T")
    .withAppealInformation(
      AppealInformation.create("UnderAppeal", "Second")
    )

  val lspPenalty2 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(3)),
    penaltyOrder = "3",
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  val lspPenalty3 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(2)),
    penaltyOrder = "2",
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  val lspPenalty4 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(1)),
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1, lspPenalty2, lspPenalty3, lspPenalty4)
  ))

  override def optComplianceData: Option[CompliancePayload] = Some(
    CompliancePayload.apply(nino)
      .withObligationDetail(ReportingPeriod(2029, None), false)
      .withObligationDetail(ReportingPeriod(2029, Some(3)), false)
      .withObligationDetail(ReportingPeriod(2028, None), false)
      .withObligationDetail(ReportingPeriod(2028, Some(2)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(1)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(0)), false)
      .withObligationDetail(ReportingPeriod(2027, None), false)
      .withObligationDetail(ReportingPeriod(2027, Some(3)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(2)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(1)), true)
  )
  override val nino: String = "AA411110A"
  override val description: String = "LSP4 - penalty due, under second stage appeal"
  override val descriptionOverdue: Option[String] = Some("LSP4 - penalty overdue, under second stage appeal")
  override val timemachineDate: String = "01/02/2028"
  override val timeMachineDateOverdue: Option[String] = Some("01/05/2028")
  override val mtdItId: String = "411110"
  override val utr: String = "0000411110"
}
