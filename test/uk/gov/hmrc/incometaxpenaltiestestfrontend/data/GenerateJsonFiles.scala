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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.UserData._

class GenerateJsonFiles extends AnyWordSpec
with Matchers
with GuiceOneAppPerSuite {

  val allUsersPenaltyData: Map[String, UserDetailsData] = Map(
    nino0 -> AA000000A,
    nino1 -> AA100000A,
    nino2 -> AA111110A,
    nino3 -> AA123450A,
    nino4 -> AA200000A,
    nino5 -> AA200010A,
    nino6 -> AA789120A,
    nino7 -> AA222220A,
    nino8 -> AA233330A,
    nino9 -> AA244440A,
    nino10 -> AA300000A,
    nino11 -> AA789130A,
    nino12 -> AA500000A
  )

  allUsersPenaltyData.foreach { case (nino, userDetails) =>
    s"print json models for nino $nino" should {
      s"create and write json models for nino $nino" in {
        val stubData = userDetails.stubData()
        val penaltyDetailsJson = userDetails.getJson(
          stubData.penaltyDetails
        )
//        println("*************PENALTY DETAILS*************")
//        println(penaltyDetailsJson)

        val (financialDetailsJson, hipFinancialDetailsJson) = stubData.financialDetails
          .fold(("No DETAILS", "NO DETAILS"))(fd =>
            (userDetails.getJson(fd, false), userDetails.getJson(fd))
        )
//        println("*************FINANCIAL DETAILS*************")
//        println(financialDetailsJson)
//        println("*************FINANCIAL DETAILS HIP*************")
//        println(hipFinancialDetailsJson)

        val complianceDetailsJson = stubData.complianceData
          .fold("No DETAILS")(cd =>
            userDetails.getJson(cd)
          )
//        println("*************COMPLIANCE DETAILS*************")
//        println(complianceDetailsJson)
      }
    }
  }



}
