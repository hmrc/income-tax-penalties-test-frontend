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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LPP, LPPDetails, TimeToPay, Totalisations}

import java.time.LocalDate

object AC200000D extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      penalisedPrincipalTotal = 2000,
      lppPostedTotal = 120,
      lppEstimatedTotal = 10.41
    )
  )

  private val principalChargeReference = "XJ002616061095"
  private val timeToPay = Some(TimeToPay(Some(LocalDate.of(2028, 3, 20)), None))

  private val latePaymentPenaltyDetails1: LPPDetails = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    ReportingPeriod(2027, None),
    amount = 120.00,
    optChargeRef = Some(principalChargeReference)
  ).copy(
    lpp1LRCalculationAmt = Some(2000),
    lpp1HRCalculationAmt = Some(2000),
    penaltyChargeCreationDate = Some("2028-03-02"),
    penaltyChargeDueDate = Some("2028-04-03")
  ).withTimeToPay(timeToPay)

  private val latePaymentPenaltyDetails2: LPPDetails = LatePaymentPenaltyDetails.lpp2DueOrOverdue(
    ReportingPeriod(2027, None),
    10.41,
    principalChargeReference
  ).copy(
    lpp1LRCalculationAmt = Some(2000),
    lpp1HRCalculationAmt = Some(2000),
    penaltyChargeCreationDate = Some("2028-03-02"),
    penaltyChargeDueDate = Some("2028-04-03"),
    principalChargeLatestClearing = None
  ).withTimeToPay(timeToPay)

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountAccruingInterest = None,
      totalAccountPostedInterest = Some(180.00)
    )
  )

  override val lpp: Option[LPP] = Some(LPP(
    manualLPPIndicator = false,
    lppDetails = Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2))
  ))

  override val nino: String = "AC200000D"
  override val mtdItId: String = "20000"
  override val utr: String = "1000020000"
  override val description: String = "TTP - 2 LPPs - (1 LPP2 DUE - TTP Proposed, 1 LPP1 DUE - WIP PDD+726)"
  override val descriptionOverdue: Option[String] = Some("TTP - 2 LPPs - (1 LPP2 OVERDUE - TTP Proposed, 1 LPP1 OVERDUE - WIP PDD+726)")
  override val timemachineDate: String = "30/03/2028"
  override val timeMachineDateOverdue: Option[String] = Some("20/05/2028")
}
