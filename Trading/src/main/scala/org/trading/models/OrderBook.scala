package org.trading.models

import scala.collection.mutable

object OrderBook {

  type Entry[O] = (Price.Level, PriceBucket[O])
  type PriceBuckets[O] = mutable.TreeMap[Price.Level, PriceBucket[O]]

  final case class Book[S <: Price, O <: Order.Type](side: S,
                                                     prices: PriceBuckets[O] ) {
    def quantityAt(priceLevel: Price.Level): Long = prices.get(priceLevel).fold(0L)(_.quantity)
  }

  implicit val BookTypeOrderManagement: OrderManagement[Book[_, _]] = new OrderManagement[Book[_, _]] {
    override def add[O <: Order.Type](t: Book[_, _], order: O): Unit = ???

    override def remove[O <: Order.Type](t: Book[_, _], order: O): Unit = ???
  }


}


final case class OrderBook[O <: Order.Type](symbol: String,
                                            asks: OrderBook.Book[Price.Ask.type, O],
                                            bids: OrderBook.Book[Price.Bid.type, O]
                                           ) {
  //  def addOne(symbol: String, order: Order.LimitedOrder)

  def best(s: Price): Long = s match {
    // best price for ask is the min price
    case Price.Ask => asks.prices.keys.min
    // best price for bid is the max price
    case Price.Bid => bids.prices.keys.max
  }

  def priceBuckets(s: Price): Iterator[OrderBook.Entry[O]] = s match {
    case Price.Ask => asks.prices.toSeq.iterator
    case Price.Bid => bids.prices.toSeq.reverseIterator
  }

  def quantityAt(price: Price, level: Price.Level): Long = price match {
    case Price.Ask => asks.quantityAt(level)
    case Price.Bid => bids.quantityAt(level)
  }


}