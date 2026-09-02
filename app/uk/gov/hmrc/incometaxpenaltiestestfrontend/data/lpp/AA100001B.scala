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

object AA100001B extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppEstimatedTotal = 120
    )
  )

  private val principalChargeReference = "XJ002616061022"
  private val principalChargeReference2 = "XJ002616061090"

  val latePaymentPenaltyDetails1: LPPDetails = LatePaymentPenaltyDetails.lpp2Penalty(
    ReportingPeriod(2024, None),
    1.64,
    principalChargeReference
  ).withChargeReference(principalChargeReference).copy(lpp1LRCalculationAmt = Some(2000), lpp1HRCalculationAmt = Some(1500), penaltyChargeCreationDate = Some("2025-03-02"), penaltyChargeDueDate = Some("2025-04-03"))

  val latePaymentPenaltyDetails2: LPPDetails = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    ReportingPeriod(2024, None),
    amount = 105.00
  ).withChargeReference("XJ002616061022").copy(lpp1LRCalculationAmt = Some(2000), lpp1HRCalculationAmt = Some(1500), penaltyChargeCreationDate = Some("2025-03-02"), penaltyChargeDueDate = Some("2025-04-03"))

  val latePaymentPenaltyDetails3: LPPDetails = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    ReportingPeriod(2024, None),
    amount = 15
  ).withChargeReference(principalChargeReference2).copy(lpp1LRCalculationAmt = Some(500),lpp1HRCalculationAmt = Some(500), penaltyChargeCreationDate = Some("2025-05-02"), penaltyChargeDueDate = Some("2025-04-06")).withSupplementary(supplement = Some(true))


  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountAccruingInterest = Some(1.64),
      totalAccountPostedInterest = Some(120.00)
    )
  )

  override val lpp: Option[LPP] = Some(LPP(
    manualLPPIndicator = false,
    lppDetails = Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2, latePaymentPenaltyDetails3))
  ))

  override val nino: String = "AA100001B"
  override val mtdItId: String = "10000"
  override val utr: String = "1000010000"
  override val description: String = "3 LPPs - (1 LPP2 ESTIMATE, 1 LPP1 DUE, 1 LPP1 supplementary DUE) with supplementary charge"
  override val descriptionOverdue: Option[String] = Some("3 LPPs - (1 LPP2 ESTIMATE, 1 LPP1 OVERDUE, 1 LPP1 supplementary OVERDUE) with supplementary charge")
  override val timemachineDate: String = "05/03/2025"
  override val timeMachineDateOverdue: Option[String] = Some("20/05/2025")
}
