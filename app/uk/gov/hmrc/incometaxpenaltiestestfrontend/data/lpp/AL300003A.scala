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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{AppealInformation, LPP, Totalisations}

object AL300003A extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppEstimatedTotal = 120.00
    )
  )

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountAccruingInterest = Some(120.00)
    )
  )

  val reportingPeriod1 = ReportingPeriod(2027, None)
  val reportingPeriod2 = ReportingPeriod(2026, None)
  val reportingPeriod3 = ReportingPeriod(2025, None)


  val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp1Penalty(
    reportingPeriod3,
    amount = 40.00,
    isTaxPaid = false
  ).withAppealInformation(AppealInformation.create("UnderAppeal", "First"))

  val latePaymentPenaltyDetails2 = LatePaymentPenaltyDetails.lpp2DueOrOverdue(
      reportingPeriod3,
      amount = 40.00,
      latePaymentPenaltyDetails1.principalChargeReference)
    .withIncomeTaxPaid(reportingPeriod3, false)
    .withAppealInformation(AppealInformation.create("UnderAppeal", "First"))

  val latePaymentPenaltyDetails3 = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    reportingPeriod2,
    amount = 40.00
  ).withAppealInformation(AppealInformation.create("UnderAppeal", "Second"))

  val latePaymentPenaltyDetails4 = LatePaymentPenaltyDetails.lpp2DueOrOverdue(
      reportingPeriod2,
      amount = 40.00,
      latePaymentPenaltyDetails3.principalChargeReference)
    .withIncomeTaxPaid(reportingPeriod2, false)
    .withAppealInformation(AppealInformation.create("UnderAppeal", "Second"))

  val latePaymentPenaltyDetails5 = LatePaymentPenaltyDetails.lpp1Paid(
    reportingPeriod1,
    amount = 40.00
  ).withAppealInformation(AppealInformation.create("UnderAppeal", "Tribunal"))

  val latePaymentPenaltyDetails6 = LatePaymentPenaltyDetails.lpp2Paid(
      reportingPeriod1,
      amount = 40.00,
      latePaymentPenaltyDetails5.principalChargeReference)
    .withIncomeTaxPaid(reportingPeriod1, false)
    .withAppealInformation(AppealInformation.create("UnderAppeal", "Tribunal"))


  override val lpp = Some(LPP(
    manualLPPIndicator = false,
    lppDetails =  Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2,
      latePaymentPenaltyDetails3, latePaymentPenaltyDetails4, latePaymentPenaltyDetails5, latePaymentPenaltyDetails6))
  ))

  override val nino: String = "AL300003A"
  override val mtdItId: String = "10000"
  override val utr: String = "1000010000"
  override val description: String = "multiple LPP with under appeal status'"
  override val timemachineDate: String = "05/03/2028"
}
