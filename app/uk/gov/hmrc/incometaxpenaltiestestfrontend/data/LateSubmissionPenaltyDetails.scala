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
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{AppealInformation, LSPDetails, LateSubmission}

object LateSubmissionPenaltyDetails {

  def active(reportingPeriod: ReportingPeriod,
             penaltyOrder: String = "1",
             returnSubmitted: Boolean = false,
             addAdditionalIncomeSource: Boolean = false): LSPDetails = {
    val lateSubmissions = LateSubmission.create(reportingPeriod,
      returnSubmitted,
      addAdditionalIncomeSource
    )
    LSPDetails.create(
      reportingPeriod = reportingPeriod,
      penaltyOrder = penaltyOrder
    ).withLateSubmission(lateSubmissions)
  }

  def expired(reportingPeriod: ReportingPeriod,
              penaltyOrder: String = "1",
              addAdditionalIncomeSource: Boolean = false): LSPDetails = {
    val lateSubmissions = LateSubmission.create(reportingPeriod,
      true,
      addAdditionalIncomeSource
    )
    LSPDetails.create(
      reportingPeriod = reportingPeriod,
      penaltyOrder = penaltyOrder,
      penaltyStatus = Some("Inactive")
    ).withLateSubmission(lateSubmissions)
      .withExpiryReason("NAT")
  }

  def paid(reportingPeriod: ReportingPeriod,
           chargeAmount: BigDecimal,
           penaltyOrder: String = "1",
           addAdditionalIncomeSource: Boolean = false): LSPDetails = {
    val lateSubmissions = LateSubmission.create(reportingPeriod,
      true,
      addAdditionalIncomeSource
    )
    LSPDetails.create(
        reportingPeriod = reportingPeriod,
        penaltyOrder = penaltyOrder
      )
      .withLateSubmission(lateSubmissions)
      .withChargeAmount(chargeAmount)
  }

  def due(reportingPeriod: ReportingPeriod,
          chargeAmount: BigDecimal,
          penaltyOrder: String = "1",
          returnSubmitted: Boolean = false,
          addAdditionalIncomeSource: Boolean = false): LSPDetails = {
    val lateSubmissions = LateSubmission.create(reportingPeriod,
      returnSubmitted,
      addAdditionalIncomeSource
    )
    LSPDetails.create(
        reportingPeriod = reportingPeriod,
        penaltyOrder = penaltyOrder
      ).withLateSubmission(lateSubmissions)
      .withChargeAmount(chargeAmount)
      .withChargeOutstandingAmount(chargeAmount)
  }

  def overdue(reportingPeriod: ReportingPeriod,
              penaltyOrder: String = "1",
              returnSubmitted: Boolean = false,
              addAdditionalIncomeSource: Boolean = false): LSPDetails = {
    val lateSubmissions = LateSubmission.create(reportingPeriod,
      returnSubmitted,
      addAdditionalIncomeSource
    )
    LSPDetails.create(
      reportingPeriod = reportingPeriod,
      penaltyOrder = penaltyOrder
    ).withLateSubmission(lateSubmissions)
  }

  def cancelledLateSubmissionPenalty(reportingPeriod: ReportingPeriod,
                                     penaltyOrder: String = "1",
                                     addAdditionalIncomeSource: Boolean = false,
                                     appealLevel: String = "First",
                                     expiryReason: String = "APP"): LSPDetails = {
    val lateSubmissions = LateSubmission.create(reportingPeriod,
      true,
      addAdditionalIncomeSource
    )
    val appealInformation = AppealInformation.create("Upheld", appealLevel)

    LSPDetails.create(
        reportingPeriod = reportingPeriod,
        penaltyOrder = penaltyOrder,
        penaltyStatus = Some("Inactive")
      ).withLateSubmission(lateSubmissions)
      .withExpiryReason(expiryReason)
      .withAppealInformation(appealInformation)
  }

  def adjusted(reportingPeriod: ReportingPeriod,
               penaltyOrder: String = "1",
               isRemoved: Boolean = false): LSPDetails = {
    val lspDetails = if(isRemoved) {
      cancelledLateSubmissionPenalty(reportingPeriod, penaltyOrder, expiryReason = "FAP")
    } else {
      active(reportingPeriod, penaltyOrder)
    }
    lspDetails.withAdjustment()
  }
}
