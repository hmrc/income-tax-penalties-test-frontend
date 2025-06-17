/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.financialData

import play.api.libs.json.{Format, Json}

case class FinancialData(
                              totalisation: Option[FinancialDetailsTotalisation] = None,
                              documentDetails: Option[Seq[DocumentDetails]] = None
                            )

object FinancialData {

  def create(totalAccountOverdue: Option[BigDecimal] = Some(0.00),
            totalAccountPostedInterest: Option[BigDecimal] = Some(0.00),
            totalAccountAccruingInterest: Option[BigDecimal] = Some(0.00)): FinancialData = {
    FinancialData(
      totalisation = Some(FinancialDetailsTotalisation.create(
        totalAccountOverdue,
        totalAccountPostedInterest,
        totalAccountAccruingInterest
      ))
    )
  }
  implicit val format: Format[FinancialData] = Json.format[FinancialData]
}
