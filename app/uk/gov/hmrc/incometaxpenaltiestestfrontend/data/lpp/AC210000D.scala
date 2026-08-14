/*
 * Copyright 2026 HM Revenue & Customs
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

object AC210000D extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      penalisedPrincipalTotal = 2000,
      lppPostedTotal = 126.02
    )
  )

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountAccruingInterest = None,
      totalAccountPostedInterest = Some(126.02)
    )
  )

  private val principalChargeReference = "XJ002616061096"

  private val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    ReportingPeriod(2027, None),
    amount = 120.00,
    optChargeRef = Some(principalChargeReference)
  ).copy(
    lpp1LRCalculationAmt = Some(2000),
    lpp1HRCalculationAmt = Some(2000),
    penaltyChargeCreationDate = Some("2028-03-02"),
    penaltyChargeDueDate = Some("2028-04-03"),
    principalChargeLatestClearing = Some("2028-03-20")
  ).withTimeToPay(Some(TimeToPay(
    TTPProposalDate = Some(LocalDate.of(2028, 3, 12)),
    TTPAgreementDate = Some(LocalDate.of(2028, 3, 15))
  )))

  private val latePaymentPenaltyDetails2 = LatePaymentPenaltyDetails.lpp2DueOrOverdue(
    ReportingPeriod(2027, None),
    6.02,
    principalChargeReference
  ).copy(
    lpp1LRCalculationAmt = Some(2000),
    lpp1HRCalculationAmt = Some(2000),
    penaltyChargeCreationDate = Some("2028-03-02"),
    penaltyChargeDueDate = Some("2028-04-03"),
    principalChargeLatestClearing = Some("2028-03-20")
  ).withTimeToPay(Some(TimeToPay(
    TTPProposalDate = Some(LocalDate.of(2028, 3, 12)),
    TTPAgreementDate = Some(LocalDate.of(2028, 3, 15))
  )))

  override val lpp: Option[LPP] = Some(LPP(
    manualLPPIndicator = false,
    lppDetails = Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2))
  ))

  override val nino: String = "AC210000D"
  override val mtdItId: String = "20000"
  override val utr: String = "1000020000"
  override val description: String = "TTP - 2 LPPs - (1 LPP2 DUE - 1 LPP1 DUE - IT Paid) TTP Proposed + TTP Agreed"
  override val timemachineDate: String = "25/03/2028"
}

