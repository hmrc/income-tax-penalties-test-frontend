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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{AppealInformation, LPP}

object AA200010A extends UserDetailsData {

  lazy val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp2Penalty(
    ReportingPeriod(2027, None),
    2.19,
    latePaymentPenaltyDetails2.principalChargeReference
  ).withAppealInformation(
    AppealInformation.create("Rejected", "First")
  )

  val latePaymentPenaltyDetails2 = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    ReportingPeriod(2027, None),
    amount = 80.00,
    optChargeRef = Some("XJ002616061089")
  ).withAppealInformation(
    AppealInformation.create("Rejected", "First")
  )

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

  override val nino: String = "AA200010A"
  override val mtdItId: String = "20001"
  override val utr: String = "1000020001"
  override val description: String = "2 LPPs - (1 LPP ESTIMATE, 1 LPP DUE, 1 LPP PAID) *Second Stage Appeal*"
  override val descriptionOverdue: Option[String] = Some("2 LPPs - (1 LPP ESTIMATE, 1 LPP OVERDUE, 1 LPP PAID)")
  override val timemachineDate: String = "02/02/2028"
  override val timeMachineDateOverdue: Option[String] = Some("20/05/2028")
}
