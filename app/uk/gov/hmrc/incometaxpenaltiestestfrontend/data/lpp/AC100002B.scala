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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LPP, LPPDetails, TimeToPay, Totalisations}

import java.time.LocalDate


object AC100002B extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppEstimatedTotal = 120
    )
  )

  lazy val latePaymentPenaltyDetails1: LPPDetails = LatePaymentPenaltyDetails.lpp2Penalty(
    ReportingPeriod(2024, None),
    1.64,
    latePaymentPenaltyDetails2.principalChargeReference
  )
  val latePaymentPenaltyDetails2: LPPDetails = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    ReportingPeriod(2024, None),
    amount = 105.00
  ).withTimeToPay(Some(TimeToPay(TTPProposalDate = Some(LocalDate.of(2025, 2, 17)), TTPAgreementDate = None)))

  val latePaymentPenaltyDetails3: LPPDetails = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    ReportingPeriod(2024, None),
    amount = 15
  ).withSupplementary(supplement = Some(true)).withTimeToPay(Some(TimeToPay(TTPProposalDate = Some(LocalDate.of(2025, 2, 17)), TTPAgreementDate = None)))

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

  override val nino: String = "AC100002B"
  override val mtdItId: String = "10000"
  override val utr: String = "1000010000"
  override val description: String = "3 LPPs - TTP Proposed LPP1 (1 LPP2 ESTIMATE, 1 LPP1 DUE, 1 LPP1 DUE) with supplementary charge"
  override val descriptionOverdue: Option[String] = Some("3 LPPs - TTP Proposed LPP1 (1 LPP2 ESTIMATE, 1 LPP1 OVERDUE, 1 LPP1 OVERDUE) with supplementary charge")
  override val timemachineDate: String = "05/03/2025"
  override val timeMachineDateOverdue: Option[String] = Some("20/05/2026")
}
