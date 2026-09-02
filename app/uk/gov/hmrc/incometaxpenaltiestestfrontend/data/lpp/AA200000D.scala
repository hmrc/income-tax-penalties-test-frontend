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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LPP, LPPDetails, Totalisations}

object AA200000D extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      totalAccountOverdue = 60.00,
      totalAccountAccruingInterest = 0.81
    )
  )

  private val principalChargeReference = "XJ002616061062"
  private val principalChargeReference2 = "XJ002616061014"

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountPostedInterest = Some(8.20)
    )
  )

  val latePaymentPenaltyDetails1: LPPDetails = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    ReportingPeriod(2025, None),
      amount = 2580.00
  ).withChargeReference(principalChargeReference).copy(
    principalChargeDueDate = "2026-05-16",
    penaltyChargeDueDate = Some("2026-07-09"),
    lpp1LRCalculationAmt = Some(129000),
    lpp1HRCalculationAmt = Some(129000),
    penaltyAmountPosted = 2580,
    penaltyAmountOutstanding = Some(2580),
    penaltyChargeCreationDate = Some("2026-04-16"),
    lpp1LRDays = Some("16"),
    lpp1LRPercentage = Some(2.00),
    lpp1HRPercentage = Some(2.00),
    principalChargeMainTr = "4915",
    supplement = Some(false)

  )

  val latePaymentPenaltyDetails2: LPPDetails = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    ReportingPeriod(2025, None),
      amount = 2580.00
  ).withChargeReference(principalChargeReference2).copy(
    principalChargeDueDate = "2026-05-16",
    penaltyChargeDueDate = Some("2026-07-09"),
    lpp1LRCalculationAmt = Some(129000),
    lpp1HRCalculationAmt = Some(129000),
    penaltyAmountPosted = 2580,
    penaltyAmountOutstanding = Some(2580),
    penaltyChargeCreationDate = Some("2026-04-16"),
    lpp1LRDays = Some("16"),
    lpp1LRPercentage = Some(2.00),
    lpp1HRPercentage = Some(2.00),
    principalChargeMainTr = "4915",
    supplement = Some(true)

  )

  override val lpp = Some(LPP(
    manualLPPIndicator = false,
    lppDetails =  Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2))
  ))

  override val nino: String = "AA200000D"
  override val mtdItId: String = "10000"
  override val utr: String = "1000010000"
  override val description: String = "TTP - 2 LPP - 15-30 days, tax unpaid, 1 LPP with supplementary charge"
  override val timemachineDate: String = "21/02/2026"
}

