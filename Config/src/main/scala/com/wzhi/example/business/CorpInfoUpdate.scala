package com.wzhi.example.business

object CorpInfoUpdate extends App {
  @inline def validateAddress(data: CorpData): CorpData =
    if(data == null || data.address == null || data.address.isEmpty)
      throw new RuntimeException("The company's address cannot be empty.")
    else data

  @inline def validateCorp(data: CorpData): CorpData =
    if(data == null || data.name == null || data.name.isEmpty)
      throw new RuntimeException("The company's name cannot be empty.")
    else data

  @inline def updateAddress(updateData: CorpData, oldData: CorpData): CorpData = {
    oldData.copy(address = updateData.address)
  }

  @inline def printCorpInfo(data: CorpData): CorpData = {
    println("Company information: " + data)
    data
  }
}
