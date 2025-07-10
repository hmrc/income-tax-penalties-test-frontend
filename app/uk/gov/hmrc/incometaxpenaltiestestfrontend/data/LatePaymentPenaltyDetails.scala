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

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lpp.AL100001B.reportingPeriod1
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.ReportingPeriod
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{AppealInformation, LPPDetails}

object LatePaymentPenaltyDetails {

  def lpp1Penalty(reportingPeriod: ReportingPeriod,
                  amount: BigDecimal,
                  isDay15: Boolean = false): LPPDetails = {
    LPPDetails.create(
      reportingPeriod,
      "LPP1",
      status = if(isDay15) "A" else "P",
      amount = amount
    ).withLPP1CalculationFields(isDay15)
  }

  def lpp2Penalty(reportingPeriod: ReportingPeriod,
                  amount: BigDecimal): LPPDetails = {
    LPPDetails.create(
      reportingPeriod,
      "LPP2",
      amount = amount
    )
      .withLPP1CalculationFields(false)
      .withLpp2CalculationFields()
  }

  def lpp1Paid(reportingPeriod: ReportingPeriod,
               amount: BigDecimal,
               isDay15: Boolean = false): LPPDetails = {
    LPPDetails.create(
      reportingPeriod,
      "LPP1",
      "P",
      amount = amount
    )
     .withIncomeTaxPaid(reportingPeriod.getIncomeTaxPaidDate(if(isDay15) 20 else 35))
    .withLPP1CalculationFields(isDay15)
    .withFullyPaidAmount(reportingPeriod.defaultPenaltyPaidDate(isDay15))
  }

  def lpp2Paid(reportingPeriod: ReportingPeriod,
               amount: BigDecimal): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP2",
        "P",
        amount = amount
      )
      .withIncomeTaxPaid(reportingPeriod.getIncomeTaxPaidDate(35))
      .withLPP1CalculationFields(false)
      .withLpp2CalculationFields()
      .withFullyPaidAmount(reportingPeriod.defaultPenaltyPaidDate(false))
  }

  def lpp1DueOrOverdue(reportingPeriod: ReportingPeriod,
                       amount: BigDecimal,
                       isDay15: Boolean = false): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP1",
        "P",
        amount = amount
      )
      .withLPP1CalculationFields(isDay15)
      .withFullyDueAmount()
  }



  def lpp2DueOrOverdue(reportingPeriod: ReportingPeriod,
                       amount: BigDecimal): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP2",
        "P",
        amount = amount
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
        amount = amount
      )
      .withIncomeTaxPaid(reportingPeriod.getIncomeTaxPaidDate(if(isDay15) 20 else 35))
      .withLPP1CalculationFields(isDay15)
      .withPartiallyPaidAmount(amountPaid)
  }

  def lpp2PartiallyPaid(reportingPeriod: ReportingPeriod,
                        amount: BigDecimal,
                        amountPaid: BigDecimal): LPPDetails = {
    LPPDetails.create(
        reportingPeriod,
        "LPP2",
        "P",
        amount = amount
      )
      .withIncomeTaxPaid(reportingPeriod.getIncomeTaxPaidDate(35))
      .withLPP1CalculationFields(false)
      .withLpp2CalculationFields()
      .withPartiallyPaidAmount(amountPaid)
  }

  def lpp1Cancelled(reportingPeriod: ReportingPeriod,
                    amount: BigDecimal,
                    isDay15: Boolean = false,
                    appealLevel: String = "First"): LPPDetails = {

    val appealInformation = AppealInformation.create("Upheld", appealLevel)

    lpp1Penalty(reportingPeriod, amount, isDay15)
      .withAppealInformation(appealInformation)
  }



  def lpp2Cancelled(reportingPeriod: ReportingPeriod,
                    amount: BigDecimal,
                    appealLevel: String = "First"): LPPDetails = {

    val appealInformation = AppealInformation.create("Upheld", appealLevel)

    lpp2Penalty(reportingPeriod, amount)
      .withAppealInformation(appealInformation)
  }



//  def overdue(reportingPeriod: ReportingPeriod,
//              penaltyOrder: String = "1",
//              returnSubmitted: Boolean = false,
//              addAdditionalIncomeSource: Boolean = false): LSPDetails = {
//    val lateSubmissions = LateSubmission.create(reportingPeriod,
//      returnSubmitted,
//      addAdditionalIncomeSource
//    )
//    LSPDetails.create(
//      reportingPeriod = reportingPeriod,
//      penaltyOrder = penaltyOrder
//    ).withLateSubmission(lateSubmissions)
//  }
}
