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

object AL100001B extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppEstimatedTotal = 40.00
    )
  )

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountAccruingInterest = Some(40.00)
    )
  )

  val reportingPeriod1 = ReportingPeriod(2025, None)

  val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp1Paid(
    reportingPeriod1,
    amount = 80.00
  )

  val latePaymentPenaltyDetails2 = LatePaymentPenaltyDetails.lpp2Paid(
    reportingPeriod1,
    amount = 40.00)
  
  override val lpp = Some(LPP(
    manualLPPIndicator = false,
    lppDetails =  Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2))
  ))

  override val nino: String = "AL100001B"
  override val mtdItId: String = "10000"
  override val utr: String = "1000010000"
  override val description: String = "1 LPP - 31 + days, tax paid and penalty paid"
  override val timemachineDate: String = "20/03/2026"
}
