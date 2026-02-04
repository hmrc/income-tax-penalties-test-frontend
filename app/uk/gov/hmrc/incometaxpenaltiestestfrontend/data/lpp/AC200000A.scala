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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LPP, TimeToPay, Totalisations}

import java.time.LocalDate

object AC200000A extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      totalAccountOverdue = 40.00
    )
  )

  private val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    ReportingPeriod(2027, None),
    amount = 80.00
  )

  private val latePaymentPenaltyDetails2 = LatePaymentPenaltyDetails.lpp2Penalty(
    ReportingPeriod(2027, None),
    2.19,
    latePaymentPenaltyDetails1.principalChargeReference
  ).withTimeToPay(Some(TimeToPay(None, Some(LocalDate.of(2026, 2, 16)))))


  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountAccruingInterest = Some(2.19),
      totalAccountPostedInterest = Some(80.00)
    )
  )

  override val lpp: Option[LPP] = Some(LPP(
    manualLPPIndicator = false,
    lppDetails = Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2))
  ))

  override val nino: String = "AC200000A"
  override val mtdItId: String = "20000"
  override val utr: String = "1000020000"
  override val description: String = "2 LPPs - (LPP2 ESTIMATE - TTP Agreed, LPP1 DUE)"
  override val timemachineDate: String = "04/04/2028"
}
