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

object AA300000A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 3
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(2)),
    penaltyOrder = "3",
    returnSubmitted = true
  ).withAppealInformation(
    AppealInformation.create("Rejected", "First")
  )
  val lspPenalty2 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(1)),
    penaltyOrder = "2"
  )
  val lspPenalty3 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(0))
  ).withAppealInformation(
    AppealInformation.create("Rejected", "Second")
  ).copy(penaltyNumber = "005000001015")

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty3, lspPenalty2, lspPenalty1)
  ))

  override def optComplianceData: Option[CompliancePayload] = None

  override val nino: String = "AA300000A"
  override val mtdItId: String = "30000"
  override val utr: String = "1000030000"
  override val description: String = "3 LSPs - (3 LSP ACTIVE) *Second Stage Appeal*"
  override val timemachineDate: String = "30/11/2027"
}
