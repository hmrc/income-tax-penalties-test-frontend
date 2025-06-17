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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails

import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.JsonUtils

import java.security.SecureRandom

case class LPPDetails(principalChargeReference: String,
                      penaltyStatus: String,
                      penaltyAmountAccruing: BigDecimal,
                      penaltyAmountPosted: BigDecimal,
                      principalChargeBillingFrom: String,
                      principalChargeBillingTo: String,
                      principalChargeDueDate: String,
                      principalChargeDocNumber: String = "DOC1",
                      principalChargeMainTr: String = "4720",
                      principalChargeSubTr: String = "SUB1",
                      penaltyCategory: Option[String] = None,
                      penaltyAmountPaid: Option[BigDecimal] = None,
                      penaltyAmountOutstanding: Option[BigDecimal] = None,
                      lpp1LRCalculationAmt: Option[BigDecimal] = None,
                      lpp1LRDays: Option[String] = None,
                      lpp1LRPercentage: Option[BigDecimal] = None,
                      lpp1HRCalculationAmt: Option[BigDecimal] = None,
                      lpp1HRDays: Option[String] = None,
                      lpp1HRPercentage: Option[BigDecimal] = None,
                      lpp2Days: Option[String] = None,
                      lpp2Percentage: Option[BigDecimal] = None,
                      penaltyChargeCreationDate: Option[String] = None,
                      communicationsDate: Option[String] = None,
                      penaltyChargeReference: Option[String] = None,
                      penaltyChargeDueDate: Option[String] = None,
                      appealInformation: Option[Seq[AppealInformation]] = None,
                      principalChargeLatestClearing: Option[String] = None,
                      timeToPay: Option[Seq[TimeToPay]] = None
                     ) {

  lazy val penaltyAmount = if(penaltyStatus == "A") penaltyAmountAccruing else penaltyAmountPosted

  def withLPP1CalculationFields(isDay15: Boolean): LPPDetails = {
    if(isDay15) {
      val calculationAmount = penaltyAmount * 50
      withLpp1LR(calculationAmount)
    } else {
      val calculationAmount = penaltyAmount * 25
      withLpp1HR(calculationAmount)
    }
  }

  def withLpp1LR(calculationAmount: BigDecimal): LPPDetails =
    copy(
      lpp1LRDays = Some("15"),
      lpp1LRPercentage = Some(2.00),
      lpp1LRCalculationAmt = Some(calculationAmount)
    )

  def withLpp1HR(calculationAmount: BigDecimal): LPPDetails =
    copy(
      lpp1LRDays = Some("15"),
      lpp1LRPercentage = Some(2.00),
      lpp1LRCalculationAmt = Some(calculationAmount),
      lpp1HRDays = Some("30"),
      lpp1HRPercentage = Some(2.00),
      lpp1HRCalculationAmt = Some(calculationAmount)
    )

  def withLpp2CalculationFields(): LPPDetails =
    copy(
      lpp2Days = Some("31"),
      lpp2Percentage = Some(4.00)
    )

  def withFullyPaidAmount(): LPPDetails =
    copy(
      penaltyAmountOutstanding = Some(0.00),
      penaltyAmountPaid = Some(penaltyAmount),
      penaltyChargeDueDate = None
    )

  def withPartiallyPaidAmount(amountPaid: BigDecimal): LPPDetails = {
    copy(
      penaltyAmountOutstanding = Some(penaltyAmount - amountPaid),
      penaltyAmountPaid = Some(amountPaid)
    )
  }

  def withFullyDueAmount(): LPPDetails =
    copy(
      penaltyAmountOutstanding = Some(penaltyAmount),
      penaltyAmountPaid = Some(0.00)
    )

  def withAppealInformation(appealInfo: AppealInformation): LPPDetails =
    copy(appealInformation = Some(Seq(appealInfo)))

}

object LPPDetails extends JsonUtils {

  lazy val secureRandom = new SecureRandom()

  def create(reportingPeriod: ReportingPeriod,
            category: String,
            status: String = "A",
            amount: BigDecimal): LPPDetails = {
    val randomInt = secureRandom.nextInt(100) + 1000
    val principleChargeRef = "XJ00261606" + randomInt.toString
    val (amountAccruing, amountPosted): (BigDecimal, BigDecimal) = status match {
      case "A" => (amount, 0.00)
      case _ => (0.00, amount)
    }
    LPPDetails(
      principalChargeReference = principleChargeRef,
      penaltyStatus = status,
      penaltyAmountAccruing = amountAccruing,
      penaltyAmountPosted = amountPosted,
      principalChargeBillingFrom = reportingPeriod.taxPeriodStartDate,
      principalChargeBillingTo = reportingPeriod.taxPeriodEndDate,
      principalChargeDueDate = reportingPeriod.taxPeriodDueDate,
      penaltyCategory = Some(category),
      penaltyChargeReference = Some(principleChargeRef),
      penaltyChargeCreationDate = Some(reportingPeriod.penaltyChargeCreationDate),
      penaltyChargeDueDate = Some(reportingPeriod.penaltyChargeDueDate)
    )
  }
}
