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
      penaltyOrder = Some(penaltyOrder)
    ).withLateSubmission(lateSubmissions)
  }

  def expired(reportingPeriod: ReportingPeriod,
              addAdditionalIncomeSource: Boolean = false,
              thresholdMet: Boolean = false): LSPDetails = {
    val lateSubmissions = LateSubmission.create(reportingPeriod,
      true,
      addAdditionalIncomeSource
    )
    val defaultDetails = LSPDetails.create(
      reportingPeriod = reportingPeriod,
      penaltyOrder = None,
      penaltyStatus = Some("Inactive")
    ).withLateSubmission(lateSubmissions)
      .withExpiryReason("NAT")

    if(thresholdMet) {
      defaultDetails
        .withPenaltyCategory("T")
        .withCharge(200.00, reportingPeriod.penaltyChargeDueDate)
    } else defaultDetails
  }

  def paid(reportingPeriod: ReportingPeriod,
           chargeAmount: BigDecimal = 200.00,
           penaltyOrder: String = "1",
           addAdditionalIncomeSource: Boolean = false): LSPDetails = {
    val lateSubmissions = LateSubmission.create(reportingPeriod,
      true,
      addAdditionalIncomeSource
    )
    LSPDetails.create(
        reportingPeriod = reportingPeriod,
        penaltyOrder = Some(penaltyOrder)
      )
      .withLateSubmission(lateSubmissions)
      .withCharge(chargeAmount, reportingPeriod.penaltyChargeDueDate)
  }

  def dueOrOverdue(reportingPeriod: ReportingPeriod,
                   chargeAmount: BigDecimal = 200.00,
                   penaltyOrder: String = "1",
                   returnSubmitted: Boolean = false,
                   addAdditionalIncomeSource: Boolean = false): LSPDetails = {
    val lateSubmissions = LateSubmission.create(reportingPeriod,
      returnSubmitted,
      addAdditionalIncomeSource
    )
    LSPDetails.create(
        reportingPeriod = reportingPeriod,
        penaltyOrder = Some(penaltyOrder)
      ).withLateSubmission(lateSubmissions)
      .withCharge(chargeAmount, reportingPeriod.penaltyChargeDueDate)
      .withChargeOutstandingAmount(chargeAmount)
  }

  def cancelledLateSubmissionPenalty(reportingPeriod: ReportingPeriod,
                                     addAdditionalIncomeSource: Boolean = false,
                                     appealLevel: String = "First",
                                     expiryReason: String = "APP",
                                     thresholdMet: Boolean = false): LSPDetails = {
    val lateSubmissions = LateSubmission.create(reportingPeriod,
      true,
      addAdditionalIncomeSource
    )
    val appealInformation = AppealInformation.create("Upheld", appealLevel)

    val defaultDetails = LSPDetails.create(
        reportingPeriod = reportingPeriod,
        penaltyOrder = None,
        penaltyStatus = Some("Inactive")
      ).withLateSubmission(lateSubmissions)
      .withExpiryReason(expiryReason)
      .withAppealInformation(appealInformation)

    if(thresholdMet) {
      defaultDetails
        .withPenaltyCategory("T")
        .withCharge(200.00, reportingPeriod.penaltyChargeDueDate)
    } else defaultDetails
  }

  def adjusted(reportingPeriod: ReportingPeriod,
               penaltyOrder: String = "1"): LSPDetails = {
    active(reportingPeriod, penaltyOrder)
      .withAdjustment()
  }

  def removedFollowingAdjusted(reportingPeriod: ReportingPeriod): LSPDetails = {
    cancelledLateSubmissionPenalty(reportingPeriod, expiryReason = "FAP")
      .withAdjustment()
  }
}
