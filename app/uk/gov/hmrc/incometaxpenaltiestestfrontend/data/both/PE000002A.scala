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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.data.both

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.{LatePaymentPenaltyDetails, LateSubmissionPenaltyDetails, UserDetailsData}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.complianceData.CompliancePayload
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.financialData.FinancialData
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LPP, LSP, LSPSummary, Totalisations}

object PE000002A extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppEstimatedTotal = 92.04
    )
  )

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountAccruingInterest = Some(92.04)
    )
  )

  val reportingPeriod1 = ReportingPeriod(2028, None)

  val lspSummary = LSPSummary(
    activePenaltyPoints = 1
  )

  val lspPenalty1 = LateSubmissionPenaltyDetails.active(
    ReportingPeriod(2027, Some(3)),
    returnSubmitted = true
  )
  val lppPenalty1 = LatePaymentPenaltyDetails.lpp1Penalty(
    reportingPeriod1,
    amount = 46.02
  ).withChargeReference("PE888881102202")

  val lppPenalty2 = LatePaymentPenaltyDetails.lpp2Penalty(
    reportingPeriod1,
    amount = 46.02)
    .withChargeReference("PE888881102201")
    .withIncomeTaxPaid(reportingPeriod1.getIncomeTaxPaidDate(35))


  override val lsp: Option[LSP] = Some(LSP(
    lspSummary = lspSummary,
    lspDetails = Seq(lspPenalty1)
  ))

  override val lpp = Some(LPP(
    manualLPPIndicator = false,
    lppDetails =  Some(Seq(lppPenalty1, lppPenalty2))
  ))


  override def optComplianceData: Option[CompliancePayload] = None
  override val nino: String = "PE000002A"
  override val mtdItId: String = "XTIT52155169861"
  override val utr: String = "1000010000"
  override val description: String = "1 LSP, 2 LPP - 15-30 days and 31days +, tax unpaid"
  override val timemachineDate: String = "02/02/2028"
}
