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

object AB311110A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 3
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(0)),
    penaltyOrder = "3",
    addAdditionalIncomeSource = true)

  val lspPenalty2 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2026, Some(3)),
    penaltyOrder = "2",
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  val lspPenalty3 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2026, None),
    returnSubmitted = true,
    addAdditionalIncomeSource = true).withAppealInformation(
    AppealInformation.create("Rejected", "Second")
  )

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1, lspPenalty2, lspPenalty3)
  ))

  override def optComplianceData: Option[CompliancePayload] = None
  override val nino: String = "AB311110A"
  override val mtdItId: String = "311110"
  override val utr: String = "10000311110"
  override val description: String = "3 LSPs - (3 LSP ACTIVE, one of which a return)"
  override val timemachineDate: String = "30/05/2027"
}
