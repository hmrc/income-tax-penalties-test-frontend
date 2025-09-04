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

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object AA233440A extends UserDetailsData {

  lazy val latePaymentPenaltyDetails1 = LatePaymentPenaltyDetails.lpp2DueOrOverdue(
    ReportingPeriod(2027, None),
    46.02,
    "XJ002616061069"
  )

  lazy val latePaymentPenaltyDetails2 = LatePaymentPenaltyDetails.lpp2Penalty(
    ReportingPeriod(2027, None),
      amount = 46.02,
    "PE888881102201")

  override def optFinancialData(): Option[FinancialData] = Some(
    FinancialData.create(
      totalAccountPostedInterest = Some(46.02),
      totalAccountAccruingInterest = Some(46.02)
    )
  )

  override val lpp: Option[LPP] = Some(LPP(
    manualLPPIndicator = false,
    lppDetails = Some(Seq(latePaymentPenaltyDetails1, latePaymentPenaltyDetails2))
  ))

  override val nino: String = "AA233440A"
  override val mtdItId: String = "23344"
  override val utr: String = "1000023344"
  override val description: String = "Charge Period LPP2 Display"
  override val descriptionOverdue: Option[String] = Some("Charge Period LPP2 Display")
  override val timemachineDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
  override val timeMachineDateOverdue: Option[String] = Some("20/05/2028")

}
