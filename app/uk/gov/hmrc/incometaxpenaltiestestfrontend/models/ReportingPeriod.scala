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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.models

import java.time.LocalDate
import java.time.format.DateTimeFormatter

case class ReportingPeriod(startYear: Int, optQuarter: Option[Int]) {

  val taxYearStart = optQuarter match {
    case Some(quarter) => LocalDate.of(startYear, quarter * 3 + 1, 6)
    case _ => LocalDate.of(startYear, 4, 6)
  }

  val taxYearEnd = optQuarter match {
    case None => LocalDate.of(startYear + 1, 4, 5)
    case Some(quarter) =>
      val (endYear, nextQuarter) = if(quarter == 3) {
        (startYear + 1, 0)
      } else (startYear, quarter + 1)
      LocalDate.of(endYear, nextQuarter * 3 + 1, 5)
  }
  val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
  val taxPeriodStartDate = taxYearStart.format(dateTimeFormatter)
  val taxPeriodEndDate = taxYearEnd.format(dateTimeFormatter)
  val taxPeriodDueDate = taxYearEnd.plusMonths(1).minusDays(5).format(dateTimeFormatter)
  val penaltyChargeCreationDate = taxYearEnd.plusMonths(1).minusDays(4).format(dateTimeFormatter)
  val penaltyChargeDueDate = taxYearEnd.plusMonths(2).minusDays(5).format(dateTimeFormatter)
  val penaltyExpiryDate = taxYearEnd.plusYears(2).format(dateTimeFormatter)
  val defaultRecievedDate = taxYearEnd.plusMonths(1).minusDays(6).format(dateTimeFormatter)
}
