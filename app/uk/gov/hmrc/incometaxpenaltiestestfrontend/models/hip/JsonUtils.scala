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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip

import play.api.libs.json._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.hip.penaltyDetails.{AppealInformation, LPPDetails, TimeToPay}

trait JsonUtils {
  def jsonObjNoNulls(fields: (String, Json.JsValueWrapper)*): JsObject =
    JsObject(Json.obj(fields:_*).fields.filterNot(_._2 == JsNull).filterNot(_._2 == Json.obj()))

  implicit val formatLppDetails: Format[LPPDetails] = new Format[LPPDetails] {
    override def reads(json: JsValue): JsResult[LPPDetails] = {
      for {
        principalChargeReference <- (json \ "principalChargeReference").validate[String]
        penaltyStatus <- (json \ "penaltyStatus").validate[String]
        penaltyAmountAccruing <- (json \ "penaltyAmountAccruing").validate[BigDecimal]
        penaltyAmountPosted <- (json \ "penaltyAmountPosted").validate[BigDecimal]
        principalChargeBillingFrom <- (json \ "principalChargeBillingFrom").validate[String]
        principalChargeBillingTo <- (json \ "principalChargeBillingTo").validate[String]
        principalChargeDueDate <- (json \ "principalChargeDueDate").validate[String]
        principalChargeDocNumber <- (json \ "principalChargeDocNumber").validate[String]
        principalChargeMainTr <- (json \ "principalChargeMainTr").validate[String]
        principalChargeSubTr <- (json \ "principalChargeSubTr").validate[String]
        penaltyCategory <- (json \ "penaltyCategory").validateOpt[String]
        penaltyAmountPaid <- (json \ "penaltyAmountPaid").validateOpt[BigDecimal]
        penaltyAmountOutstanding <- (json \ "penaltyAmountOutstanding").validateOpt[BigDecimal]
        lpp1LRCalculationAmt <- (json \ "lpp1LRCalculationAmt").validateOpt[BigDecimal]
        lpp1LRDays <- (json \ "lpp1LRDays").validateOpt[String]
        lpp1LRPercentage <- (json \ "lpp1LRPercentage").validateOpt[BigDecimal]
        lpp1HRCalculationAmt <- (json \ "lpp1HRCalculationAmt").validateOpt[BigDecimal]
        lpp1HRDays <- (json \ "lpp1HRDays").validateOpt[String]
        lpp1HRPercentage <- (json \ "lpp1HRPercentage").validateOpt[BigDecimal]
        lpp2Days <- (json \ "lpp2Days").validateOpt[String]
        lpp2Percentage <- (json \ "lpp2Percentage").validateOpt[BigDecimal]
        penaltyChargeCreationDate <- (json \ "penaltyChargeCreationDate").validateOpt[String]
        communicationsDate <- (json \ "communicationsDate").validateOpt[String]
        penaltyChargeReference <- (json \ "penaltyChargeReference").validateOpt[String]
        penaltyChargeDueDate <- (json \ "penaltyChargeDueDate").validateOpt[String]
        appealInformation <- (json \ "appealInformation").validateOpt[Seq[AppealInformation]]
        principalChargeLatestClearing <- (json \ "principalChargeLatestClearing").validateOpt[String]
        timeToPay <- (json \ "timeToPay").validateOpt[TimeToPay]
      }
      yield {
        LPPDetails(principalChargeReference, penaltyStatus, penaltyAmountAccruing, penaltyAmountPosted, principalChargeBillingFrom,
          principalChargeBillingTo, principalChargeDueDate, principalChargeDocNumber, principalChargeMainTr, principalChargeSubTr, penaltyCategory, penaltyAmountPaid,
          penaltyAmountOutstanding, lpp1LRCalculationAmt, lpp1LRDays, lpp1LRPercentage, lpp1HRCalculationAmt, lpp1HRDays, lpp1HRPercentage,
          lpp2Days, lpp2Percentage, penaltyChargeCreationDate, communicationsDate,
          penaltyChargeReference, penaltyChargeDueDate, appealInformation, principalChargeLatestClearing, timeToPay)
      }
    }

    override def writes(o: LPPDetails): JsValue = {
      jsonObjNoNulls(
        "principalChargeReference" -> o.principalChargeReference,
        "penaltyStatus" -> o.penaltyStatus,
        "penaltyAmountAccruing" -> o.penaltyAmountAccruing,
        "penaltyAmountPosted" -> o.penaltyAmountPosted,
        "principalChargeBillingFrom" -> o.principalChargeBillingFrom,
        "principalChargeBillingTo" -> o.principalChargeBillingTo,
        "principalChargeDueDate" -> o.principalChargeDueDate,
        "principalChargeDocNumber" -> o.principalChargeDocNumber,
        "principalChargeMainTr" -> o.principalChargeMainTr,
        "principalChargeSubTr" -> o.principalChargeSubTr,
        "penaltyCategory" -> o.penaltyCategory,
        "penaltyAmountPaid" -> o.penaltyAmountPaid,
        "penaltyAmountOutstanding" -> o.penaltyAmountOutstanding,
        "lpp1LRCalculationAmt" -> o.lpp1LRCalculationAmt,
        "lpp1LRDays" -> o.lpp1LRDays,
        "lpp1LRPercentage" -> o.lpp1LRPercentage,
        "lpp1HRCalculationAmt" -> o.lpp1HRCalculationAmt,
        "lpp1HRDays" -> o.lpp1HRDays,
        "lpp1HRPercentage" -> o.lpp1HRPercentage,
        "lpp2Days" -> o.lpp2Days,
        "lpp2Percentage" -> o.lpp2Percentage,
        "penaltyChargeCreationDate" -> o.penaltyChargeCreationDate,
        "communicationsDate" -> o.communicationsDate,
        "penaltyChargeReference" -> o.penaltyChargeReference,
        "penaltyChargeDueDate" -> o.penaltyChargeDueDate,
        "appealInformation" -> o.appealInformation,
        "principalChargeLatestClearing" -> o.principalChargeLatestClearing,
        "timeToPay" -> o.timeToPay
      )
    }
  }
}
