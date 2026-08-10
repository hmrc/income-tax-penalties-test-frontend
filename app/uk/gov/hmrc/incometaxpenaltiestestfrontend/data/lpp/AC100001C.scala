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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LPP, TimeToPay, Totalisations}

import java.time.LocalDate

object AC100001C extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppEstimatedTotal = 60.00
    )
  )

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountAccruingInterest = Some(60.00)
    )
  )

  val reportingPeriod1 = ReportingPeriod(2025, None)

  val latePaymentPenaltyDetails1 = {
    LatePaymentPenaltyDetails.lpp1PartiallyPaid(
      reportingPeriod1,
      amount = 60.00,
      amountPaid = 20.00,
      isDay15 = true
    ).withTimeToPay(Some(TimeToPay(TTPProposalDate = Some(LocalDate.of(2026, 2, 17)), TTPAgreementDate = None)))
  }
  
  override val lpp = Some(LPP(
    manualLPPIndicator = false,
    lppDetails =  Some(Seq(latePaymentPenaltyDetails1))
  ))

  override val nino: String = "AC100001C"
  override val mtdItId: String = "10000"
  override val utr: String = "1000010000"
  override val description: String = "1 LPP - 15-30 days, partly paid - (1 LPP1 PART DUE) TTP proposed"
  override val descriptionOverdue: Option[String] = Some("1 LPP - 31+ days, partly paid - (1 LPP1 PART OVERDUE) TTP proposed")
  override val timemachineDate: String = "02/03/2026"
  override val timeMachineDateOverdue: Option[String] = Some("28/03/2026")
}
