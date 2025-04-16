package uk.gov.hmrc.incometaxpenaltiestestfrontend.data

import uk.gov.hmrc.incometaxpenaltiestestfrontend.models.UserRecord

object UserData {
  val nino1 = "AA100000A"
  val userRecord1 = UserRecord(nino1, "10000", "1000000000", "1 LPP")


  val allUserRecords: Map[String, UserRecord] = Map(
    nino1 -> userRecord1
  )

}
