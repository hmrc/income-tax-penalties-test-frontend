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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp1

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.{LateSubmissionPenaltyDetails, UserDetailsData}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.complianceData.CompliancePayload
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LSP, LSPSummary}

object AA111110B extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 1
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.adjusted(
    ReportingPeriod(2027, Some(1)))

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1)
  ))

  override def optComplianceData: Option[CompliancePayload] = None
  override val nino: String = "AA111110B"
  override val mtdItId: String = "11111"
  override val utr: String = "1000011111"
  override val description: String = "1 LSP Update - (ADJUSTED)"
  override val timemachineDate: String = "01/08/2027"
}
