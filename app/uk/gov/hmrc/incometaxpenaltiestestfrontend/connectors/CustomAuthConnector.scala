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

import play.api.Logger
import play.api.http.Status.{CREATED, TOO_MANY_REQUESTS}
import play.api.libs.json._
import uk.gov.hmrc.auth.core.PlayAuthConnector
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse, StringContextOps, TooManyRequestException}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.config.AppConfig
import uk.gov.hmrc.incometaxpenaltiestestfrontend.connectors.LoginUtil._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.UserData
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.UserRecord

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.collection.Seq
import scala.concurrent.{ExecutionContext, Future}

case class GovernmentGatewayToken(gatewayToken: String)

object GovernmentGatewayToken {
  implicit val reads: Reads[GovernmentGatewayToken] = {
    Json.reads[GovernmentGatewayToken]
  }
}

case class AuthExchange(bearerToken: String, sessionAuthorityUri: String)

@Singleton
class CustomAuthConnector @Inject()(appConfig: AppConfig,
                                    val http: HttpClientV2)
                                   (implicit ec: ExecutionContext) extends PlayAuthConnector {
  override val serviceUrl: String = appConfig.authLoginServiceUrl

  override def httpClientV2: HttpClientV2 = http

  def login(userRecord: UserRecord, isAgent: Boolean)(implicit hc: HeaderCarrier): Future[(AuthExchange, GovernmentGatewayToken)] = {
    val createdPayload = if (userRecord.nino.contains("No Nino")) {
      createPayloadNoNino(userRecord)
    } else {
      createPayload(userRecord, isAgent)
    }
    loginRequest(createdPayload)
  }

  def loginRequest(payload: JsValue)(implicit hc: HeaderCarrier): Future[(AuthExchange, GovernmentGatewayToken)] = {

    def getHeader(headerName: String, headers: Map[String, Seq[String]]): Option[String] = {
      val headerNames = List(headerName, headerName.take(1).toUpperCase() + headerName.drop(1))
      headerNames.flatMap(name => headers.get(name)).headOption.map(_.mkString)
    }

    val sessionUrl = s"$serviceUrl/government-gateway/session/login"

    http
      .post(url"$sessionUrl")
      .withBody(payload)
      .execute[HttpResponse] flatMap {
      case response@HttpResponse(CREATED, _, headers) =>
        (
          getHeader("authorization", headers),
          getHeader("location", headers),
          (response.json \ "gatewayToken").asOpt[String]
        ) match {
          case (Some(token), Some(sessionUri), Some(receivedGatewayToken)) =>
            Future.successful((AuthExchange(token, sessionUri), GovernmentGatewayToken(receivedGatewayToken)))
          case (token, sessionUri, gatewayToken) =>
            Logger("application").info("HEADERS: " + headers)
            Logger("application").info("response json:" + response.json)
            Logger("application").info(s"login response info: $token :: $sessionUri :: $gatewayToken")
            Future.failed(new RuntimeException("Internal Error, missing headers or gatewayToken in response from auth-login-api"))
        }
      case response@HttpResponse(TOO_MANY_REQUESTS, _, _) =>
        Future.failed(new TooManyRequestException(s"response from $serviceUrl/government-gateway/session/login was ${response.status}. Body ${response.body}"))
      case response =>
        Future.failed(new RuntimeException(s"response from $serviceUrl/government-gateway/session/login was ${response.status}. Body ${response.body}"))
    }
  }


  private def createPayloadNoNino(userRecord: UserRecord): JsValue = {
    Json.obj(
      "credId" -> UUID.randomUUID().toString,
      "affinityGroup" -> {
        "Individual"
      },
      "confidenceLevel" -> 250,
      "credentialStrength" -> "strong",
      "credentialRole" -> "User",
      "usersName" -> "usersName",
      "enrolments" -> getEnrolmentData(isAgent = false, userRecord)
    ) ++ {
      removeEmptyValues(
        "groupIdentifier" -> Some("groupIdentifier"),
        "gatewayToken" -> Some("gatewayToken"),
        "agentId" -> Some("agentId"),
        "agentCode" -> Some("agentCode"),
        "agentFriendlyName" -> Some("agentFriendlyName"),
        "email" -> Some("email")
      )
    }
  }

  private def createPayload(userRecord: UserRecord, isAgent: Boolean): JsValue = {
    val delegateEnrolments = getDelegatedEnrolmentData(isAgent = isAgent, userRecord)
    Json.obj(
      "credId" -> UUID.randomUUID(),
      "affinityGroup" -> (if (isAgent) "Agent" else "Individual"),
      "confidenceLevel" -> 250,
      "credentialStrength" -> "strong",
      "credentialRole" -> "User",
      "usersName" -> "usersName",
      "enrolments" -> getEnrolmentData(isAgent = isAgent, userRecord),
      "delegatedEnrolments" -> delegateEnrolments
    ) ++ {
      if (isAgent) {
        removeEmptyValues(
          "email" -> Some("user@test.com")
        ) ++
          Json.obj(
            "gatewayInformation" -> JsObject.empty)

      } else {
        removeEmptyValues(
          "nino" -> Some(userRecord.nino),
          "groupIdentifier" -> Some("groupIdentifier"),
          "gatewayToken" -> Some("gatewayToken"),
          "agentId" -> Some("agentId"),
          "agentCode" -> Some("agentCode"),
          "agentFriendlyName" -> Some("agentFriendlyName"),
          "email" -> Some("email")
        )
      }
    }
  }

  private def removeEmptyValues(fields: (String, Option[String])*): JsObject = {
    val onlyDefinedFields = fields
      .collect {
        case (key, Some(value)) => key -> Json.toJsFieldJsValueWrapper(value)
      }
    Json.obj(onlyDefinedFields: _*)
  }
}