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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lpp

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.{LatePaymentPenaltyDetails, UserDetailsData}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.financialData.FinancialData
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LPP, Totalisations}

object AA210002C extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppEstimatedTotal = 120
    )
  )

  lazy val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp2Penalty(
    ReportingPeriod(2027, None),
    2.19,
    latePaymentPenaltyDetails2.principalChargeReference
  )

  val latePaymentPenaltyDetails2 = LatePaymentPenaltyDetails.lpp1PartiallyPaid(
    ReportingPeriod(2027, None),
    amount = 60.00,
    amountPaid = 20.00,
  )

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountAccruingInterest = Some(2.19),
      totalAccountPostedInterest = Some(120.00)
    )
  )

  override val lpp: Option[LPP] = Some(LPP(
    manualLPPIndicator = false,
    lppDetails = Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2))
  ))

  override val nino: String = "AA210002C"
  override val mtdItId: String = "20000"
  override val utr: String = "1000020000"
  override val description: String = " 31+ days,tax unpaid but penalty partly paid (1LPP1 £40.00 due, 1LPP2 Estimate)"
  override val timemachineDate: String = "05/03/2028"
}
