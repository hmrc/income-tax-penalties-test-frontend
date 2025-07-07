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

import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.UserRecord

object UserData {

  val nino0: String = "AA000000A"
  val userRecord0: UserRecord = UserRecord(nino0, "00000", "1000000000", "No penalties")

  val nino1: String = "AA100000A"
  val userRecord1: UserRecord = UserRecord(nino1, "10000", "1000010000", "1 LPP - (ESTIMATE)")

  val nino2: String = "AA111110A"
  val userRecord2: UserRecord = UserRecord(nino2, "11111", "1000011111", "1 LSP - (ACTIVE)")

  val nino3: String = "AA123450A"
  val userRecord3: UserRecord = UserRecord(nino3, "12345", "1000012345", "1 LPP paid + 1 LPP unpaid - (1 LPP ESTIMATE, 1 LPP PAID)")

  val nino4: String = "AA200000A"
  val userRecord4: UserRecord = UserRecord(nino4, "20000", "1000020000", "2 LPPs - (1 LPP ESTIMATE, 1 LPP DUE, 1 LPP PAID)")

  val nino5: String = "AA200010A"
  val userRecord5: UserRecord = UserRecord(nino5, "20001", "1000020001", "2 LPPs - (1 LPP ESTIMATE, 1 LPP DUE, 1 LPP PAID) *Second Stage Appeal*")

  val nino6: String = "PE000000A"
  val userRecord6: UserRecord = UserRecord(nino6, "XTIT12345678912", "1000078912", "2 LSPs - (2 LSP ACTIVE)")

  val nino7: String = "AA222220A"
  val userRecord7: UserRecord = UserRecord(nino7, "22222", "1000022222", "2nd increased LPP - (1 LPP ESTIMATE, 1 LPP DUE, 1 LPP PAID)")

  val nino8: String = "AA233330A"
  val userRecord8: UserRecord = UserRecord(nino8, "23333", "1000023333", "2nd LPP due - (1 LPP DUE, 2 LPP PAID)")

  val nino9: String = "AA244440A"
  val userRecord9: UserRecord = UserRecord(nino9, "24444", "1000024444", "All LPPs paid - (3 LPP PAID)")

  val nino10: String = "AA300000A"
  val userRecord10: UserRecord = UserRecord(nino10, "30000", "1000030000", "3 LSPs - (3 LSP ACTIVE) *Second Stage Appeal*")

  val nino11: String = "PE000001A"
  val userRecord11: UserRecord = UserRecord(nino11, "XTIT12345678913", "1000078913", "4 LSPs - (1 LSP DUE, 3 LSP ACTIVE)")

  val nino12: String = "AA500000A"
  val userRecord12: UserRecord = UserRecord(nino12, "50000", "1000050000", "5 LSPs - (2 LSP DUE, 3 LSP ACTIVE)")

  val nino13: String = "PE000002A"
  val userRecord13: UserRecord = UserRecord(nino13, "XTIT52155169861", "1000050000", "1 LSP, 2 LPPs")

  val allUserRecords: Map[String, UserRecord] = Map(
    nino0 -> userRecord0,
    nino1 -> userRecord1,
    nino2 -> userRecord2,
    nino3 -> userRecord3,
    nino4 -> userRecord4,
    nino5 -> userRecord5,
    nino6 -> userRecord6,
    nino7 -> userRecord7,
    nino8 -> userRecord8,
    nino9 -> userRecord9,
    nino10 -> userRecord10,
    nino11 -> userRecord11,
    nino12 -> userRecord12,
    nino13 -> userRecord13
  )

}
