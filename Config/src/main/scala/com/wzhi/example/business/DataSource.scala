package com.wzhi.example.business

object DataSource {
  val corps = List(CorpData("RBC", "155 xxxx Road"), CorpData("CIBC", "150 Younge St."))
  def getCorp(name: String): CorpData = corps.find(corp => corp.name == name).get // Just for example
}
