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

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.both.PE000002A
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lpp._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp0._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp1._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp2._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp3._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp4._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp5._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.UserRecord

object UserData {

  val noPenaltiesData: Seq[UserDetailsData] = List(AA000000A)
  val lsp0UserData: Seq[UserDetailsData] = List(
    AA000000B,
    AA000000C,
    AA000040A,
    AA000041A,
    AA000042A,
    AA000050A,
    AB000000B,
    AB000000C,
    AB000040A,
    AB000041A,
    AB000042A,
    AB000050A
  )

  val lsp1UserData: Seq[UserDetailsData] = List(
    AA111110A,
    AA111110B,
    AA111120A,
    AA111121A,
    AA111122A,
    AA111130A,
    AA111131A,
    AA111132A,
    AA121110A,
    AB111110A,
    AB111110B,
    AB111120A,
    AB111121A,
    AB111122A,
    AB111130A,
    AB111131A,
    AB111132A,
    AB121110A
  )

  val lsp2UserData: Seq[UserDetailsData] = List(
    AA211110A,
    AA211120A,
    AA211130A,
    AB211110A,
    AB211120A,
    PE000000A
  )

  val lsp3UserData: Seq[UserDetailsData] = List(
    AA300000A,
    AA311110A,
    AB311110A,
    AB311120A,
    AB311130A,
    AB311140A
  )

  val lsp4UserData: Seq[UserDetailsData] = List(
    AA400000A,
    AA411110A,
    AB400010A,
    AB400020A,
    AB411110A,
    AB411145A,
    PE000001A,
    PE000003A
  )

  val lsp5UserData: Seq[UserDetailsData] = List(
    AA500000A,
    AA500000B,
    AA511110A,
    AB500010A,
    AB511110A,
    AB511120A,
    AB511130A,
    AB511140A,
    AB611150A
  )

  val lppUserData: Seq[UserDetailsData] = List(
    AA100000A,
    AA100000B,
    AA100000C,
    AA120000C,
    AA100002C,
    AA123450A,
    AA200000A,
    AA200000C,
    AA100000D,
    AA200000B,
    AA200010A,
    AA222220A,
    AA233330A,
    AA244440A,
    AC100000A,
    AC100000B,
    AC200000A,
    AC200000B,
    AL200001A,
    AL300001A,
    AL300002A,
    AL300003A,
    AA233440A
  )

  val both = Seq(PE000002A)

  val qaUserRecord1 = UserRecord("GS013133A", "7183013133", "7183013133", "Individual User", "ignore")
  val qaUserRecord2 = UserRecord("GS013233A", "7183013233", "7183013233", "Agent User", "ignore")
  val qaUsersRecords: Map[String, UserRecord] = List(qaUserRecord1, qaUserRecord2).map(ur => ur.nino -> ur).toMap

  val allLSPUserData: Seq[UserDetailsData] = lsp0UserData ++ lsp1UserData ++ lsp2UserData ++ lsp3UserData ++ lsp4UserData ++ lsp5UserData
  val allLocalUserRecords: Map[String, UserRecord] =
    asUserRecords(noPenaltiesData) ++ asUserRecords(allLSPUserData) ++ asUserRecords(lppUserData) ++ asUserRecords(both)

  val allUserRecords: Boolean => Map[String, UserRecord] = displayQAUsers => {
    if(displayQAUsers) qaUsersRecords else allLocalUserRecords
  }
  def asUserRecords(userDetailsData: Seq[UserDetailsData]): Map[String, UserRecord] = userDetailsData
    .foldLeft[Map[String, UserRecord]](Map.empty[String, UserRecord])(_ ++ _.userRecords())

  val penaltyTypesToDataMap: Map[String, Seq[UserDetailsData]] = Map(
    "LSP" -> allLSPUserData,
    "LPP" -> lppUserData,
    "Both" -> both,
    "No Penalties" -> noPenaltiesData)

  val lspNumsToDataMap: Map[String, Seq[UserDetailsData]] = Map(
    "LSP0" -> lsp0UserData,
    "LSP1" -> lsp1UserData,
    "LSP2" -> lsp2UserData,
    "LSP3" -> lsp3UserData,
    "LSP4" -> lsp4UserData,
    "LSP5" -> lsp5UserData,
    "All" -> allLSPUserData)

  def getUserRecordsForPenaltyType(penType: String, optLspNumber: Option[String]): Map[String, UserRecord] = {
    (penType, optLspNumber) match {
      case ("LSP", Some(lspNum)) => lspNumsToDataMap.get(lspNum).fold(allUserRecords(false))(asUserRecords(_))
      case _ => penaltyTypesToDataMap.get(penType).fold(allUserRecords(false))(asUserRecords(_))
    }
  }
}
