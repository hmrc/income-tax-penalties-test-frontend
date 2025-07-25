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

package uk.gov.hmrc.incometaxpenaltiestestfrontend.dataGenerator

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.UserData
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp5.AA511110A

class LSPandLPPStubData extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  val lppUsers = UserData.lppUserData
  val lspUsers = UserData.allLSPUserData
  val allUsers = (lppUsers ++ lspUsers ++ UserData.both)

  (UserData.lsp5UserData ++ UserData.lsp4UserData).foreach {user =>
    s"${user.nino} user" should {
      "have correct penalty details json HIP json models" in {
        val details = user.penaltyDetails()
        val detailsJson = user.getJson(details)
        println(detailsJson)
      }

//      if(user.optFinancialData().isDefined) {
//        "have correct financial details json HIP json models" in {
//          val details = user.optFinancialDetails
//          val detailsJson = details.map(user.getJson(_))
//          println(detailsJson.getOrElse("NO DATA"))
//        }
//      }
//
//      if(user.optComplianceData.isDefined) {
//        "have correct compliance json HIP json models" in {
//          val details = user.optComplianceData
//          val detailsJson = details.map(user.getJson(_))
//          println(detailsJson.getOrElse("NO DATA"))
//        }
//      }
    }
  }

}
