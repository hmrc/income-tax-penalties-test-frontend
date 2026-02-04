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

object AA244440A extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      totalAccountOverdue = 24.00
    )
  )

  lazy val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp2PartiallyPaid(
    ReportingPeriod(2027, None),
    46.02,
    22.02,
    latePaymentPenaltyDetails2.principalChargeReference
  ).copy(communicationsDate = Some("2024-03-21"))

  val latePaymentPenaltyDetails2 = LatePaymentPenaltyDetails.lpp1Paid(
    ReportingPeriod(2027, None),
    amount = 80.00,
    optChargeRef = Some("XJ002616061007")
  ).copy(communicationsDate = Some("2024-03-21"))

  val latePaymentPenaltyDetails3 = LatePaymentPenaltyDetails.lpp1Paid(
    ReportingPeriod(2026, None),
    amount = 40.00
  )

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(totalAccountOverdue = Some(0.00))
  )

  override val lpp: Option[LPP] = Some(LPP(
    manualLPPIndicator = false,
    lppDetails = Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2, latePaymentPenaltyDetails3))
  ))

  override val nino: String = "AA244440A"
  override val mtdItId: String = "24444"
  override val utr: String = "1000024444"
  override val description: String = "Latest LPP partially paid and other two paid"
  override val timemachineDate: String = "02/02/2028"
}
