package org.trading

import org.trading.models.Price.{Ask, Bid}

import org.trading.models.Side.{Buy, Sell}

package object models {

  trait OrderManagement[T] {
    def add[O <: Order.Type](t: T, order: O): Unit

    def remove[O <: Order.Type](t: T, order: O): Unit
  }

  implicit def fromOrderSide(side:Side ): Price = side match {
    case Buy => Ask
    case Sell => Bid
  }

}
