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

import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.financialData.FinancialData
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LPP, Totalisations}

object AA100000A extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppEstimatedTotal = 40.00
    )
  )

  override lazy val optFinancialData: Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountAccruingInterest = Some(40.00)
    )
  )

  val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp1Estimate(
    ReportingPeriod(2023, None),
    amount = 40.00,
    isDay15 = true
  )
  
  override val lpp = Some(LPP(
    manualLPPIndicator = false,
    lppDetails =  Some(Seq(latePaymentPenaltyDetails1))
  ))

  override val nino: String = "AA100000A"
}
