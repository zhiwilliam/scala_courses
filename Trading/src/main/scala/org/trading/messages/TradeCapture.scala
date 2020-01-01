package org.trading.messages

object TradeCapture {

  sealed trait State

  case object InitState extends State
  final case class SingleCapture() extends State
  final case class BatchCapture() extends State

  sealed trait Command
  sealed trait Event

  final case class AddTrade(trade: Trade) extends Command
  final case class InvalidateTrade(tradeId: String) extends Command
  final case class TradeAdded(trade: Trade, tradeId: String) extends Event
  //final case class


}
