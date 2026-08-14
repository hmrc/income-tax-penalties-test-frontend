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

object AC200000C extends UserDetailsData {

  override val totalisations: Option[Totalisations] = Some(
    Totalisations(
      lppPostedTotal = 180.00
    )
  )

  private val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp1DueOrOverdue(
    ReportingPeriod(2027, None),
    amount = 120.00
  )

  private val latePaymentPenaltyDetails2 = LatePaymentPenaltyDetails.lpp2DueOrOverdue(
    ReportingPeriod(2027, None),
    60.00,
    latePaymentPenaltyDetails1.principalChargeReference
  )

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

  override val nino: String = "AC200000C"
  override val mtdItId: String = "20000"
  override val utr: String = "1000020000"
  override val description: String = "TTP - 2 LPPs - (1 LPP2 DUE - TTP Agreed, 1 LPP1 DUE - WIP PDD+726)"
  override val descriptionOverdue: Option[String] = Some("TTP - 2 LPPs - (1 LPP2 OVERDUE - TTP Agreed, 1 LPP1 OVERDUE - WIP PDD+726)")
  override val timemachineDate: String = "30/03/2028"
  override val timeMachineDateOverdue: Option[String] = Some("20/05/2028")
}


