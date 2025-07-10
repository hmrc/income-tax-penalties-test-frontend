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

import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lpp._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp0._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp1._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp2._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp3._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp4._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.data.lsp5._
import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.UserRecord

object UserData {

  val lsp0UserData: Seq[UserDetailsData] = List(
    AA000000A,
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
    AB000042A
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
    PE000000A
  )

  val lsp3UserData: Seq[UserDetailsData] = List(
    AA300000A,
    AA311110A,
    AB311110A
  )

  val lsp4UserData: Seq[UserDetailsData] = List(
    AA400000A,
    AA411110A,
    AB400010A,
    AB400020A,
    AB411110A,
    PE000001A
  )

  val lsp5UserData: Seq[UserDetailsData] = List(
    AA500000A,
    AA511110A,
    AB500010A,
    AB511110A,
    AB511120A,
    AB511130A
  )

  val lppUserData: Seq[UserDetailsData] = List(
    AA100000A,
    AA100000B,
    AA100000C,
    AA100002C,
    AA123450A,
    AA200000A,
    AA200010A,
    AA222220A,
    AA233330A,
    AA244440A,
    AL100000A,
    AL100001A,
    AL100001B,
    AL200001A,
    AL300001A,
    AL300002A,
    AL300003A
  )

  val allLSPUserData: Seq[UserDetailsData] = lsp0UserData ++ lsp1UserData ++ lsp2UserData ++ lsp3UserData ++ lsp4UserData ++ lsp5UserData

  val allUserRecords: Map[String, UserRecord] =
    asUserRecords(allLSPUserData) ++ asUserRecords(lppUserData)

  def asUserRecords(userDetailsData: Seq[UserDetailsData]): Map[String, UserRecord] = userDetailsData
    .foldLeft[Map[String, UserRecord]](Map.empty[String, UserRecord])(_ ++ _.userRecords())


}
