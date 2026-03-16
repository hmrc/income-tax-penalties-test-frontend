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

object AL200001A extends UserDetailsData {

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

  val reportingPeriod1 = ReportingPeriod(2025, None)

  lazy val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp2DueOrOverdue(
    reportingPeriod1,
    amount = 80.00,
    latePaymentPenaltyDetails2.principalChargeReference
  ).withIncomeTaxPaid(reportingPeriod1, false)

  val latePaymentPenaltyDetails2 = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    reportingPeriod1,
    amount = 40.00)
    .withIncomeTaxPaid(reportingPeriod1, false)
  
  override val lpp = Some(LPP(
    manualLPPIndicator = false,
    lppDetails =  Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2))
  ))

  override val nino: String = "AL200001A"
  override val mtdItId: String = "10000"
  override val utr: String = "1000010000"
  override val description: String = "2 LPPs - 31 + days, tax paid - (1 LPP2 DUE, 1 LPP1 DUE)"
  override val descriptionOverdue: Option[String] = Some("2 LPPs - 31 + days, tax paid - (1 LPP2 OVERDUE, 1 LPP1 OVERDUE)")
  override val timemachineDate: String = "10/04/2026"
  override val timeMachineDateOverdue: Option[String] = Some("20/04/2026")
}
