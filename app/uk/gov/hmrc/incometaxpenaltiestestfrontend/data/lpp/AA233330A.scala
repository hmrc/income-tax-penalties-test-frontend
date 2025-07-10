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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.LPP

object AA233330A extends UserDetailsData {

  val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp2DueOrOverdue(
    ReportingPeriod(2027, None),
    46.02
  )

  val latePaymentPenaltyDetails2 = LatePaymentPenaltyDetails.lpp1Paid(
    ReportingPeriod(2027, None),
    amount = 80.00
  )

  val latePaymentPenaltyDetails3 = LatePaymentPenaltyDetails.lpp1Paid(
    ReportingPeriod(2026, None),
    amount = 40.00
  )

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountPostedInterest = Some(46.02)
    )
  )

  override val lpp: Option[LPP] = Some(LPP(
    manualLPPIndicator = false,
    lppDetails = Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2, latePaymentPenaltyDetails3))
  ))

  override val nino: String = "AA233330A"
  override val mtdItId: String = "23333"
  override val utr: String = "1000023333"
  override val description: String = "2nd LPP due - (1 LPP DUE, 2 LPP PAID)"
  override val descriptionOverdue: Option[String] = Some("2nd LPP due - (1 LPP OVERDUE, 2 LPP PAID)")
  override val timemachineDate: String = "02/02/2028"
  override val timeMachineDateOverdue: Option[String] = Some("20/05/2028")

}
