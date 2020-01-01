package org.trading.models

import java.util.UUID

object Order {

  sealed trait Type {
    def orderId: UUID
    def side: Side
    def quantity: Int
  }

  final case class LimitedOrder(symbol: String,
                                orderId: UUID,
                                side: Side,
                                var quantity: Int,
                                price: Long) extends Type

  final case class MarketOrder(symbol: String,
                               orderId: UUID,
                               side: Side,
                               var quantity: Int) extends Type


  sealed trait Result

  final case class OrderExpired(orderId: UUID) extends Result

  final case class ExecutionFailure(orderId: UUID) extends Result

  implicit val OrderOrdering = new Ordering[Type] {
    def compare(a: Type, b: Type): Int = a.quantity compare b.quantity
  }

}
