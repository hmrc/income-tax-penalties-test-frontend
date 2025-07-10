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

import play.api.libs.json.{JsString, Json, Writes}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.UserRecord
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.complianceData.CompliancePayload
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.financialData.{FinancialData, FinancialDetails}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails._

trait UserDetailsData {

  val nino: String
  val mtdItId: String
  val utr: String
  val processingDateYear: String = "2025"
  val description: String
  val timemachineDate: String

  val descriptionOverdue: Option[String] = None
  val timeMachineDateOverdue: Option[String] = None
  
  val lsp: Option[LSP] = None
  val lpp: Option[LPP] = None
  val totalisations: Option[Totalisations] = None
  val breathingSpace: Option[Seq[BreathingSpace]] = None

  def penaltyData(): PenaltyData = PenaltyData(
    totalisations, lsp, lpp, breathingSpace 
  )

  def optFinancialData(): Option[FinancialData] = None

  def optComplianceData: Option[CompliancePayload] = None

  lazy val userRecord: UserRecord = UserRecord(nino, mtdItId, utr, description, timemachineDate)

  def userRecords(): Map[String, UserRecord] = {
    (descriptionOverdue, timeMachineDateOverdue) match {
      case (Some(overdueDescript), Some(overdueDate)) => Map(
        nino -> userRecord,
        s"$nino-overdue" -> UserRecord(nino, mtdItId, utr, overdueDescript, overdueDate)
      )
      case _ => Map(nino -> userRecord)
    }
  }
  
  def penaltyDetails() = PenaltyDetails(
    processingDate = s"$processingDateYear-04-05",
    penaltyData = Some(penaltyData())
  )

  def optFinancialDetails: Option[FinancialDetails] = optFinancialData.map{financialData =>
    FinancialDetails(
      processingDate = s"$processingDateYear-04-05",
      financialData = Some(financialData)
    )
  }

  def stubData() = StubData(penaltyDetails(), optFinancialDetails, optComplianceData)
  
  def getJson[T](data: T, isCompliance: Boolean = false)(implicit writes: Writes[T]): String = {
    val payload = if(isCompliance){
      Json.obj(
        ("obligations" -> Json.arr(Json.toJson(data)))
      )
    } else {
      Json.obj(
        ("success" -> Json.toJson(data))
      )
    }
    val successJson = Json.obj(
      ("idType" -> "NINO"),
      ("idNumber" -> JsString(nino)),
      ("payload" -> payload)
    )
    Json.prettyPrint(
      Json.toJson(successJson)
    )
  }

}
