package com.wzhi.example

import com.wzhi.example.business.CorpInfoUpdate.{printCorpInfo, updateAddress, validateAddress, validateCorp}
import com.wzhi.example.business.{CorpData, DataSource}
import com.wzhi.example.business.DataSource.getCorp
import com.wzhi.example.workflow.Arrows.Arrow
import com.wzhi.example.workflow.Decorators
import DataSource._
import cats.implicits._
import Decorators._
import com.wzhi.example.workflow.implicities._

object Test extends App {

  import com.wzhi.example.workflow.implicities.OptionFlow._
  // import com.wzhi.example.workflow.implicities.ConsoleLogFlow._
  // implicit def deco[A, B](func: A => B) = createDecorator(skip[A], consoleLogTime[B], ConsoleLogFlow)(func)
  // implicit def deco2[A1, A2, B](func: (A1, A2) => B) = createDecorator2(skip2[A1, A2], consoleLogTime[B], ConsoleLogFlow)(func)

  val getOldData = getCorp _ ~> validateCorp _
  val updateCorpAddr = (validateAddress _ +> getOldData ) ~> updateAddress _ ~> printCorpInfo _

  updateCorpAddr.run(CorpData(null, "250 King St."), "RBC")
  println("-------------------------")
  updateCorpAddr.run(CorpData(null, null), "RBC")
}
