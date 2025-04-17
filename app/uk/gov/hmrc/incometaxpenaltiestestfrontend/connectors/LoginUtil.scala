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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.connectors

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.UserRecord

case class KVPair(key: String, value: String)

case class Enrolment(key: String, identifiers: Seq[KVPair], state: String)

case class DelegatedEnrolment(key: String, identifiers: Seq[KVPair], delegatedAuthRule: String)

object LoginUtil {

  implicit val kvPairWrites: Writes[KVPair] = (
    (JsPath \ "key").write[String] and
      (JsPath \ "value").write[String]
    ) (unlift(KVPair.unapply))

  implicit val enrolmentWrites: Writes[Enrolment] = (
    (JsPath \ "key").write[String] and
      (JsPath \ "identifiers").write[Seq[KVPair]] and
      (JsPath \ "state").write[String]
    ) (unlift(Enrolment.unapply))

  implicit val delegatedEnrolmentWrites: Writes[DelegatedEnrolment] = (
    (JsPath \ "key").write[String] and
      (JsPath \ "identifiers").write[Seq[KVPair]] and
      (JsPath \ "delegatedAuthRule").write[String]
    ) (unlift(DelegatedEnrolment.unapply))

  def getEnrolmentData(isAgent: Boolean, userRecord: UserRecord): JsValue = {
    val es = if (isAgent) {
      val enrolmentKey = "HMRC-AS-AGENT"
      Seq(
        Enrolment(key = enrolmentKey, identifiers =
          Seq(KVPair(key = "AgentReferenceNumber", value = "1")), state = "Activated")
      )
    } else {
      val enrolmentKey = "HMRC-MTD-IT"
      val saEnrolment = Enrolment(key = "IR-SA", identifiers =
        Seq(KVPair(key = "UTR", value = userRecord.utr)), state = "Activated")
      if(userRecord.mtditid.contains("Not Enrolled")) {
        Seq(saEnrolment)
      } else {
        Seq(Enrolment(key = enrolmentKey, identifiers =
          Seq(KVPair(key = "MTDITID", value = userRecord.mtditid)), state = "Activated"),
          saEnrolment
        )
      }
    }
    Json.toJson[Seq[Enrolment]](es)
  }

  def getDelegatedEnrolmentData(isAgent: Boolean, userRecord: UserRecord): JsValue = {
    val es = if (isAgent && !userRecord.mtditid.contains("Not Enrolled")) {
      Seq(
        DelegatedEnrolment(key = "HMRC-MTD-IT", identifiers =
          Seq(KVPair(key = "MTDITID", value = userRecord.mtditid)), delegatedAuthRule = "mtd-it-auth")
      )
    } else {
      Nil
    }
    Json.toJson[Seq[DelegatedEnrolment]](es)
  }


}
