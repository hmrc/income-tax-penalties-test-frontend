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

import java.io.{File, FileWriter}

class LSPandLPPStubData extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  val lppUsers = UserData.lppUserData
  val lspUsers = UserData.allLSPUserData
  val allUsers = (lppUsers ++ lspUsers ++ UserData.both)

  def writeToFile(path: String, detailsJson: String) = {
    val fileWriter = new FileWriter(new File(path))
    fileWriter.write(detailsJson)
    fileWriter.close()
  }

  allUsers.foreach { user =>
    if (user.nino == "AB311140A") {
      val pathStart = System.getProperty("user.dir") + s"/conf/data"
      s"${user.nino} user" should {
        "have correct penalty details json HIP json models and write to file" in {
          val path = s"$pathStart/hip_penaltiesDetailsData/${user.nino}.json"
          val details = user.penaltyDetails()
          val detailsJson = user.getJson(details)
          writeToFile(path, detailsJson)
        }

        if (user.optFinancialDetails.isDefined) {
          val financialDetails = user.optFinancialDetails.get
          "have correct financial details json HIP json models and write to file" in {
            val path = s"$pathStart/hip_financialDetailsData/${user.nino}.json"
            val detailsJson = user.getJson(financialDetails)
            writeToFile(path, detailsJson)
          }
        }

        if (user.optComplianceData.isDefined) {
          val complianceData = user.optComplianceData.get
          "have correct compliance json and write to files for local stub" in {
            val path = s"$pathStart/obligationDataStub/${user.nino}.json"
            val detailsJson = user.getJson(complianceData, isComplianceForLocalStub = true)
            writeToFile(path, detailsJson)
          }

          "have correct compliance json and write to files for penalties stub" in {
            val path = s"$pathStart/obligationData/${user.nino}.json"
            val detailsJson = user.getJson(complianceData, true)
            writeToFile(path, detailsJson)
          }
        }
      }
    }
  }

}
