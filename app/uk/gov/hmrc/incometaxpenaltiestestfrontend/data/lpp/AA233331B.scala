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

object AA233331B extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppPostedTotal = 320.81
    )
  )
  private val principalChargeReference = "XJ002616061022"

  // LPP2, paid, 19.17
  val latePaymentPenaltyDetails1: LPPDetails = LatePaymentPenaltyDetails.lpp2Paid(
    ReportingPeriod(2025, None),
    19.17,
    principalChargeReference
  ).withChargeReference(principalChargeReference).copy(
    penaltyChargeDueDate = Some("2026-04-17"),
    principalChargeLatestClearing = Some("2026-03-16")
  )

  // LPP1, paid, 300
  val latePaymentPenaltyDetails2: LPPDetails = LatePaymentPenaltyDetails.lpp1Paid(
    ReportingPeriod(2025, None),
    amount = 300
  ).withChargeReference(principalChargeReference).copy(
    penaltyChargeDueDate = Some("2026-04-04"),
    principalChargeLatestClearing = Some("2026-03-16")
  )

  // LPP2, supplement=true, paid, 1.64
  val latePaymentPenaltyDetails3: LPPDetails = LatePaymentPenaltyDetails.lpp2Paid(
    ReportingPeriod(2025, None),
    1.64,
    principalChargeReference
  ).withChargeReference(principalChargeReference).copy(
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

  override val nino: String = "AA233331B"
  override val mtdItId: String = "23333"
  override val utr: String = "1000023333"
  override val description: String = "3 LPPs - (1 LPP2 PAID, 1 LPP1 PAID, 1 supplementary LPP2 PAID) with supplementary charge"
  override val timemachineDate: String = "26/03/2026"

}

