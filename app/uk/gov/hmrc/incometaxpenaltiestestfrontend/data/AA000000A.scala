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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.data

import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LSP, LSPSummary, PenaltyDetails}

object AA000000A extends UserDetailsData {

  override val lsp: Option[LSP] = Some(
    LSP(
      lspSummary = LSPSummary(),
      lspDetails = Seq.empty
    )
  )

  override def penaltyDetails() = PenaltyDetails(
    processingDate = "2024-04-05",
    penaltyData = None
  )

  override val nino: String = "AA000000A"
}
