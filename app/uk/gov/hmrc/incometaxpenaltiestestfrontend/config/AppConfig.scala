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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.config

import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

import javax.inject.{Inject, Singleton}

@Singleton
class AppConfig @Inject()(servicesConfig: ServicesConfig) {

  val displayQAUserRecords: Boolean = servicesConfig.getBoolean("features.useQACredentials")

  val incomeTaxSessionDataUrl: String = servicesConfig.baseUrl("income-tax-session-data")

  val incomeTaxPenaltiesUrl: String = servicesConfig.baseUrl("income-tax-penalties-frontend")
  val incomeTaxPenaltiesAppealsUrl: String = servicesConfig.baseUrl("income-tax-penalties-appeals-frontend")

  val authLoginServiceUrl: String = servicesConfig.baseUrl("auth-login")

  val penaltiesHomeUrl: String = servicesConfig.getString("income-tax-penalties-frontend.home.url")
  val penaltiesAgentHomeUrl: String = servicesConfig.getString("income-tax-penalties-frontend.home.url") + "/agent"
}
