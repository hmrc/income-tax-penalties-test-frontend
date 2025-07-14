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

object PE000001A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 4,
    pocAchievementDate = Some("2028-02-28")
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2026, Some(2)),
    returnSubmitted = true
  )
  val lspPenalty2 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2026, None),
    penaltyOrder = "2"
  )
  val lspPenalty3 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(0)),
    penaltyOrder = "3",
    returnSubmitted = true
  )
  val lspPenalty4 = LateSubmissionPenaltyDetails.dueOrOverdue(
    ReportingPeriod(2027, Some(1)),
    chargeAmount = 200.00,
    penaltyOrder = "4",
    returnSubmitted = true
  ).withPenaltyCategory("T")

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty4, lspPenalty3, lspPenalty2, lspPenalty1)
  ))


  override def optComplianceData: Option[CompliancePayload] = Some(
    CompliancePayload.apply(nino)
      .withObligationDetail(ReportingPeriod(2027, Some(3)), false)
      .withObligationDetail(ReportingPeriod(2027, Some(2)), true)
      .withObligationDetail(ReportingPeriod(2027, Some(1)), true)
      .withObligationDetail(ReportingPeriod(2026, Some(3)), true)
  )
  override val nino: String = "PE000001A"
  override val mtdItId: String = "XTIT12345678913"
  override val utr: String = "1000078913"
  override val description: String = "4 LSPs - (1 LSP DUE, 3 LSP ACTIVE)"
  override val timemachineDate: String = "20/08/2027"
}
