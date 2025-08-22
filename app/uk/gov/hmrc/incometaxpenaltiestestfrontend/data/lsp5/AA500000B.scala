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

object AA500000B extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 5,
    pocAchievementDate = Some("2029-02-07")
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.dueOrOverdue(
    ReportingPeriod(2027, None),
    penaltyOrder = "5",
    returnSubmitted = true
  ).withPenaltyCategory("T").withAppealInformation(
    AppealInformation.create("UnderAppeal", "Tribunal")
  )

  val lspPenalty2 = LateSubmissionPenaltyDetails.dueOrOverdue(
    ReportingPeriod(2027, Some(2)),
    penaltyOrder = "4"
  ).withAppealInformation(
    AppealInformation.create("UnderAppeal", "Tribunal")
  )

  val lspPenalty3 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(1)),
    penaltyOrder = "3",
    returnSubmitted = true
  ).withAppealInformation(
    AppealInformation.create("UnderAppeal", "Second")
  )

  val lspPenalty4 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(0)),
    penaltyOrder = "2",
    returnSubmitted = true
  ).withAppealInformation(
    AppealInformation.create("UnderAppeal", "First")
  )

  val lspPenalty5 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2026, Some(3)),
    returnSubmitted = true
  ).withAppealInformation(
    AppealInformation.create("UnderAppeal", "Second")
  )

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty5, lspPenalty4, lspPenalty3, lspPenalty2, lspPenalty1)
  ))

  override def optComplianceData: Option[CompliancePayload] = Some(
    CompliancePayload.apply(nino)
      .withObligationDetail(ReportingPeriod(2028, None), false)
      .withObligationDetail(ReportingPeriod(2028, Some(2)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(1)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(0)), false)
      .withObligationDetail(ReportingPeriod(2027, Some(3)), false)
      .withObligationDetail(ReportingPeriod(2027, None), false)
      .withObligationDetail(ReportingPeriod(2027, Some(2)), false)
      .withObligationDetail(ReportingPeriod(2027, Some(1)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(0)), true)
      .withObligationDetail(ReportingPeriod(2026, Some(3)), true)
  )

  override val nino: String = "AA500000B"
  override val description: String = "LSP5 - latest penalty due, penalty 4 overdue, under appeal and appeal rejected"
  override val timemachineDate: String = "30/03/2028"
  override val mtdItId: String = "50000"
  override val utr: String = "1000050000"
}
