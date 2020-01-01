package org.trading.models

final case class PriceBucket[T <: Order.Type](
  priceLevel: Price.Level,
  var orders: List[T] = Nil) {
  def quantity: Long = orders.map(_.quantity.toLong).sum[Long]
}

object PriceBucket {

  def from(order: Order.MarketOrder): PriceBucket[Order.MarketOrder] = PriceBucket(0L, order :: Nil)
  def from(order: Order.LimitedOrder): PriceBucket[Order.LimitedOrder] = PriceBucket(order.price, order :: Nil)

  implicit val PriceBucketOrderManagement = new OrderManagement[PriceBucket[_]] {
    override def add[T<:Order.Type](t: PriceBucket[_], order: T): Unit = ???
    override def remove[T<:Order.Type](t: PriceBucket[_], order: T): Unit = ???
  }
}
