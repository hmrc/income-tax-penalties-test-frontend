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

object AA100002B extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppPostedTotal = 138.21
    )
  )

  private val principalChargeReference = "XJ002616061044"

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountPostedInterest= Some(138.21)
    )
  )

  val reportingPeriod1 = ReportingPeriod(2024, None)

  val latePaymentPenaltyDetails1: LPPDetails = LatePaymentPenaltyDetails.lpp1Paid(
    reportingPeriod1,
    amount = 115.00
  ).withChargeReference(principalChargeReference).copy(lpp1LRCalculationAmt = Some(2000), lpp1HRCalculationAmt = Some(1500), penaltyChargeCreationDate = Some("2025-03-03"), penaltyChargeDueDate = Some("2025-04-04"))

  val latePaymentPenaltyDetails2: LPPDetails = LatePaymentPenaltyDetails.lpp2Paid(
    reportingPeriod1,
    amount = 8.21,
    principalChargeRef = principalChargeReference
  ).withChargeReference(principalChargeReference).copy(lpp1LRCalculationAmt = Some(2000), lpp1HRCalculationAmt = Some(1500), penaltyChargeCreationDate = Some("2025-03-03"), penaltyChargeDueDate = Some("2025-04-04"))

  val latePaymentPenaltyDetails3: LPPDetails = LatePaymentPenaltyDetails.lpp1Paid(
    reportingPeriod1,
    amount = 15.00,
    isDay15 = true
  ).withChargeReference(principalChargeReference).withSupplementary(supplement = Some(true)).copy(lpp1LRCalculationAmt = Some(500), penaltyChargeCreationDate = Some("2025-03-15"), penaltyChargeDueDate = Some("2025-04-16"), communicationsDate = Some("2025-03-22"), principalChargeLatestClearing = Some("2025-03-17"))

  override val lpp = Some(LPP(
    manualLPPIndicator = false,
    lppDetails = Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2, latePaymentPenaltyDetails3))
  ))

  override val nino: String = "AA100002B"
  override val mtdItId: String = "10000"
  override val utr: String = "1000010000"
  override val description: String = "3 LPPs - (1 LPP1 PAID, 1 LPP2 PAID, 1 LPP1 supplementary PAID) with supplementary charge"
  override val timemachineDate: String = "25/03/2025"
}

