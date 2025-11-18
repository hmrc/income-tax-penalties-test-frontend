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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.data

import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{LPPDetails, TimeToPay}

object LatePaymentPenaltyDetails {

  def lpp1Penalty(reportingPeriod: ReportingPeriod,
                  amount: BigDecimal,
                  isDay15: Boolean = false,
                  isTaxPaid: Boolean,
                  ttp: Option[Seq[TimeToPay]] = None): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP1",
        status = if (isDay15 && !isTaxPaid) "A" else "P",
        amount = amount,
        isDay15To30 = isDay15
      ).withLPP1CalculationFields(isDay15)
      .withTimeToPay(ttp)
  }

  def lpp2Penalty(reportingPeriod: ReportingPeriod,
                  amount: BigDecimal,
                  principalChargeRef: String): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP2",
        amount = amount,
        chargeRef = Some(principalChargeRef)
      )
      .withLPP1CalculationFields(false)
      .withLpp2CalculationFields()
  }

  def lpp1Paid(reportingPeriod: ReportingPeriod,
               amount: BigDecimal,
               isDay15: Boolean = false,
               optChargeRef: Option[String] = None): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP1",
        "P",
        amount = amount,
        isDay15To30 = isDay15,
        chargeRef = optChargeRef
      )
      .withIncomeTaxPaid(reportingPeriod, isDay15)
      .withLPP1CalculationFields(isDay15)
      .withFullyPaidAmount(reportingPeriod.defaultPenaltyPaidDate(isDay15))
  }

  def lpp2Paid(reportingPeriod: ReportingPeriod,
               amount: BigDecimal,
               principalChargeRef: String): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP2",
        "P",
        amount = amount,
        chargeRef = Some(principalChargeRef)
      )
      .withIncomeTaxPaid(reportingPeriod, false)
      .withLPP1CalculationFields(false)
      .withLpp2CalculationFields()
      .withFullyPaidAmount(reportingPeriod.defaultPenaltyPaidDate(false))
  }

  def lpp1DueOrOverdue(reportingPeriod: ReportingPeriod,
                       amount: BigDecimal,
                       isDay15: Boolean = false,
                       optChargeRef: Option[String] = None): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP1",
        "P",
        amount = amount,
        isDay15,
        chargeRef = optChargeRef
      )
      .withLPP1CalculationFields(isDay15)
      .withFullyDueAmount()
  }


  def lpp2DueOrOverdue(reportingPeriod: ReportingPeriod,
                       amount: BigDecimal,
                       principalChargeRef: String): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP2",
        "P",
        amount = amount,
        chargeRef = Some(principalChargeRef)
      )
      .withLPP1CalculationFields(false)
      .withLpp2CalculationFields()
      .withFullyDueAmount()
  }

  def lpp1PartiallyPaid(reportingPeriod: ReportingPeriod,
                        amount: BigDecimal,
                        amountPaid: BigDecimal,
                        isDay15: Boolean = false): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP1",
        "P",
        amount = amount,
        isDay15
      )
      .withIncomeTaxPaid(reportingPeriod, isDay15)
      .withLPP1CalculationFields(isDay15)
      .withPartiallyPaidAmount(amountPaid)
  }

  def lpp2PartiallyPaid(reportingPeriod: ReportingPeriod,
                        amount: BigDecimal,
                        amountPaid: BigDecimal,
                        principalChargeRef: String): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP2",
        "P",
        amount = amount,
        chargeRef = Some(principalChargeRef)
      )
      .withIncomeTaxPaid(reportingPeriod, false)
      .withLPP1CalculationFields(false)
      .withLpp2CalculationFields()
      .withPartiallyPaidAmount(amountPaid)
  }
}
