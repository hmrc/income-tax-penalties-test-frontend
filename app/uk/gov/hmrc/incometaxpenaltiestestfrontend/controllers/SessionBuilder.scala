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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.controllers

import play.api.mvc.Session
import uk.gov.hmrc.http.{SessionId, SessionKeys}

import java.util.UUID

case class AuthExchange(bearerToken: String, sessionAuthorityUri: String)

object SessionBuilder {

  val origin = "Origin"

  def buildGGSession(authExchange: AuthExchange): Session = Session(Map(
    SessionKeys.sessionId -> SessionId(s"session-${UUID.randomUUID}").value,
    SessionKeys.authToken -> authExchange.bearerToken,
    SessionKeys.lastRequestTimestamp -> System.currentTimeMillis().toString
  ))

  def addOriginToSession(origin: Option[String], session: Session): Session = {
    origin match {
      case Some(o) => session + (SessionBuilder.origin -> o)
      case None => session
    }
  }
}
