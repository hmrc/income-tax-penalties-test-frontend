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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{AppealInformation, LSP, LSPSummary}

object AA400000A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 4,
    pocAchievementDate = Some("2029-02-28")
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.dueOrOverdue(
    ReportingPeriod(2027, None),
      penaltyOrder = "4",
    returnSubmitted = true
  ).withPenaltyCategory("T")
    .withAppealInformation(
      AppealInformation.create("UnderAppeal", "First")
    )

  val lspPenalty2 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(2)),
    penaltyOrder = "3",
    returnSubmitted = true
  )

  val lspPenalty3 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(1)),
    penaltyOrder = "2"
  )
  val lspPenalty4 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(0))
  )

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1, lspPenalty2, lspPenalty3, lspPenalty4)
  ))

  override def optComplianceData: Option[CompliancePayload] = Some(
    CompliancePayload.apply(nino)
      .withObligationDetail(ReportingPeriod(2028, None), false)
      .withObligationDetail(ReportingPeriod(2028, Some(3)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(1)), false)
      .withObligationDetail(ReportingPeriod(2028, Some(0)), false)
      .withObligationDetail(ReportingPeriod(2027, Some(3)), false)
      .withObligationDetail(ReportingPeriod(2027, None), false)
      .withObligationDetail(ReportingPeriod(2027, Some(2)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(1)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(0)), true)
  )

  override val nino: String = "AA400000A"
  override val description: String = "LSP4 - penalty due, and under first stage appeal"
  override val descriptionOverdue: Option[String] = Some("LSP4 - penalty overdue, and under first stage appeal")
  override val timemachineDate: String = "28/02/2028"
  override val timeMachineDateOverdue: Option[String] = Some("30/03/2028")
  override val mtdItId: String = "400000"
  override val utr: String = "0000400000"
}
