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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp2

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.{LateSubmissionPenaltyDetails, UserDetailsData}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.complianceData.CompliancePayload
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LSP, LSPSummary}

object AB211110A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 2,
    regimeThreshold = 2
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, None),
    penaltyOrder = "2",
    addAdditionalIncomeSource = true)

  val lspPenalty2 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2026, None),
    returnSubmitted = true,
    addAdditionalIncomeSource = true)

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1, lspPenalty2)
  ))

  override def optComplianceData: Option[CompliancePayload] = None
  override val nino: String = "AB211110A"
  override val description: String = "LSP2 Return - threshold reached"
  override val timemachineDate: String = "01/02/2028"
  override val mtdItId: String = "211110"
  override val utr: String = "0000211110"
}
