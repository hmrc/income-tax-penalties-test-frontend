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

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.UserDetailsData
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.financialData.FinancialData
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LPP, LPPDetails, Totalisations}

object AA200000D extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      totalAccountOverdue = 60.00,
      totalAccountAccruingInterest = 0.81
    )
  )

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountAccruingInterest = Some(60.00)
    )
  )

  val latePaymentPenaltyDetails1: LPPDetails = LPPDetails(
    principalChargeReference = "XJ002616061048",
    supplement = Some(false),
    penaltyStatus = "P",
    penaltyAmountAccruing = 0,
    penaltyAmountPosted = 2580.0,
    principalChargeBillingFrom = "2024-04-06",
    principalChargeBillingTo = "2025-04-05",
    principalChargeDueDate = "2026-05-16",
    principalChargeDocNumber = Some("DOC1"),
    principalChargeMainTr = "4915",
    principalChargeSubTr = Some("SUB1"),
    penaltyCategory = Some("LPP1"),
    penaltyAmountOutstanding = Some(2580.0),
    lpp1LRCalculationAmt = Some(129000.0),
    lpp1LRDays = Some("16"),
    lpp1LRPercentage = Some(2.0),
    lpp1HRCalculationAmt = Some(129000.0),
    lpp1HRDays = Some("30"),
    lpp1HRPercentage = Some(2.0),
    penaltyChargeCreationDate = Some("2026-04-16"),
    penaltyChargeReference = Some("XJ002616061048"),
    penaltyChargeDueDate = Some("2026-07-09")
  )

  val latePaymentPenaltyDetails2: LPPDetails = LPPDetails(
    principalChargeReference = "XJ002616061048",
    supplement = Some(true),
    penaltyStatus = "P",
    penaltyAmountAccruing = 0,
    penaltyAmountPosted = 2580.0,
    principalChargeBillingFrom = "2024-04-06",
    principalChargeBillingTo = "2025-04-05",
    principalChargeDueDate = "2026-05-16",
    principalChargeDocNumber = None,
    principalChargeMainTr = "4915",
    principalChargeSubTr = None,
    penaltyCategory = Some("LPP1"),
    penaltyAmountOutstanding = Some(2580.0),
    penaltyChargeCreationDate = Some("2026-04-16"),
    penaltyChargeReference = Some("XJ002616061048"),
    penaltyChargeDueDate = Some("2026-07-09")
  )

  override val lpp = Some(LPP(
    manualLPPIndicator = false,
    lppDetails = Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2))
  ))

  override val nino: String = "AA200000D"
  override val mtdItId: String = "10000"
  override val utr: String = "1000010000"
  override val description: String = "TTP - 2 LPP - 15-30 days, tax unpaid, 1 LPP with supplementary charge"
  override val timemachineDate: String = "21/02/2026"
}
