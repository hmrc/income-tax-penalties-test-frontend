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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LSP, LSPSummary}

object PE000000A extends UserDetailsData {

  val lspSummary = LSPSummary(
    activePenaltyPoints = 2
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(1)),
    returnSubmitted = true)
  val lspPenalty2 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(2)),
    penaltyOrder = "2"
  ).copy(
    communicationsDate = Some("2023-11-07"),
  )

  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty2, lspPenalty1)
  ))

  override val nino: String = "PE000000A"
  override val mtdItId: String = "XTIT12345678912"
  override val utr: String = "1000078912"
  override val description: String = "2 LSPs - (2 LSP ACTIVE)"
  override val timemachineDate: String = "30/11/2027"
}
