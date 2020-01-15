package com.zhi.resources

object StringFunctions {
  def str2KV(in: String): (String, String) = in.split("=").map(_.trim) match {
    case Array(a, b) => (a, b)
  }

  def parseProperties(in: List[String]): Map[String, String] =  in.map(str2KV).toMap
}
