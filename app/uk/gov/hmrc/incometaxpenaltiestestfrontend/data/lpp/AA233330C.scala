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

object AA233330C extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppPostedTotal = 320.81
    )
  )

  private val principalChargeReference = "XJ002616061062"

  val latePaymentPenaltyDetails1: LPPDetails = LatePaymentPenaltyDetails.lpp2PartiallyPaid(
    ReportingPeriod(2025, None),
    19.17,
    9.17,
    principalChargeReference
  ).withChargeReference(principalChargeReference).copy(
    lpp1LRCalculationAmt = Some(5000),
    lpp1HRCalculationAmt = Some(5000),
    penaltyChargeDueDate = Some("2026-04-17"),
    principalChargeLatestClearing = Some("2026-03-16")
  )

  val latePaymentPenaltyDetails2: LPPDetails = LatePaymentPenaltyDetails.lpp1PartiallyPaid(
    ReportingPeriod(2025, None),
    amount = 300,
    amountPaid = 100
  ).withChargeReference(principalChargeReference).copy(
    lpp1LRCalculationAmt = Some(5000),
    lpp1HRCalculationAmt = Some(5000),
    penaltyChargeDueDate = Some("2026-04-04"),
    principalChargeLatestClearing = Some("2026-03-16")
  )

  val latePaymentPenaltyDetails3: LPPDetails = LatePaymentPenaltyDetails.lpp2PartiallyPaid(
    ReportingPeriod(2025, None),
    1.64,
    0.64,
    principalChargeReference
  ).withChargeReference(principalChargeReference).copy(
    lpp1LRCalculationAmt = Some(5000),
    lpp1HRCalculationAmt = Some(5000),
    penaltyChargeCreationDate = Some("2026-03-20"),
    penaltyChargeDueDate = Some("2026-04-26"),
    principalChargeLatestClearing = Some("2026-03-25")
  ).withSupplementary(supplement = Some(true))


  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountOverdue = Some(0.00),
      totalAccountPostedInterest = Some(320.81)
    )
  )

  override val lpp: Option[LPP] = Some(LPP(
    manualLPPIndicator = false,
    lppDetails = Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2, latePaymentPenaltyDetails3))
  ))

  override val nino: String = "AA233330C"
  override val mtdItId: String = "23333"
  override val utr: String = "1000023333"
  override val description: String = "3 LPPs - ALL PARTIALLY PAID (1 LPP2 DUE, 1 LPP1 DUE, 1 supplementary LPP2 DUE) with supplementary charge"
  override val descriptionOverdue: Option[String] = Some("3 LPPs - ALL PARTIALLY PAID (1 LPP2 OVERDUE, 1 LPP1 OVERDUE, 1 supplementary LPP2 OVERDUE) with supplementary charge")
  override val timemachineDate: String = "26/03/2026"
  override val timeMachineDateOverdue: Option[String] = Some("20/05/2026")

}
