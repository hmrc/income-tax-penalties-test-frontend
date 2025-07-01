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

///*
// * Copyright 2024 HM Revenue & Customs
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */

package uk.gov.hmrc.incometaxpenaltiestestfrontend.connectors

import play.api.libs.json.Json
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse, StringContextOps}
import uk.gov.hmrc.incometaxpenaltiestestfrontend.config.AppConfig
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.SessionDataModel
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.SessionDataPostResponse.SessionDataPostResponse

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TimeMachineConnector @Inject()(val appConfig: AppConfig,
                                     val http: HttpClientV2
                                    )(implicit ec: ExecutionContext) extends RawResponseReads {

  def updatePenalties(timeMachineDate: String)(implicit hc: HeaderCarrier): Future[HttpResponse] = {
    lazy val url = s"${appConfig.incomeTaxPenaltiesUrl}/view-penalty/self-assessment/test-only/time-machine-now?dateToSet=$timeMachineDate"

    http
      .get(url"$url")
      .execute[HttpResponse]

  }

//  def updatePenaltiesAppeals(timeMachineDate: String)(implicit hc: HeaderCarrier): Future[HttpResponse] = {
//    lazy val url = s"${appConfig.incomeTaxPenaltiesUrl}/view-penalty/self-assessment/test-only/time-machine-now?dateToSet=$timeMachineDate"
//
//    http
//      .get(url"$url")
//      .execute[HttpResponse]
//
//  }
}
