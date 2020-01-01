package org.trading.executors

import java.time.LocalDateTime

import akka.actor.typed._
import akka.actor.typed.scaladsl._
import org.trading.messages._
import org.trading.models._

import scala.collection.mutable

import org.trading.models.Order
import org.trading.models.Order._

object OrderMatching {
  type MultiAssetBook[O<:Type] = mutable.TreeMap[String, OrderBook[O]]
  implicit class MultiAssetBookOps(val books: MultiAssetBook[_]) extends AnyVal{
    def addOne(symbol: String, order: Type): Unit = {
      //books.get(symbol).map(book => book.)
    }
  }

  def crossSpread(order: LimitedOrder,
                  book:OrderBook[LimitedOrder],
                  f: (Long, Long) => Boolean): Option[Trade]  = {
      var quantity = order.quantity
      val price: Price = order.side
      val oppPrice = !price
      val oppPrices = book.priceBuckets(!price)
      var orderMatched = Nil

      //
      if (book.best(oppPrice) > 0L && f(order.price, book.best(oppPrice)) && oppPrices.nonEmpty ){
        for {
          (_, buckets) <- oppPrices
          bucketEntry <- buckets.orders if quantity > 0 && f(order.price, bucketEntry.price)
        } yield {
          if (quantity > bucketEntry.quantity ) {
            quantity -= bucketEntry.quantity
            bucketEntry.quantity = 0
          } else {
            bucketEntry.quantity -= quantity
            quantity = 0
          }
        }
        //oppPrices.next()
//      val ( residualVolume, ordersToRemove ) =
//        crossSpreadWalk(order, opp_book, func);
//      order.volume = residual_volume;
//      for o in orders_to_remove {
//        opp_book.remove_order(o);
      }
    }

//    // if order.volume is still +ve, the can be either there is no cross-spread walk done
//    // or the cross-spread walk only filled part of the volume. In that case we continue to
//    // add the left-over volume in a new order.
//    if order.volume > 0 {
//      book.add_order(order)
//    }
    //  if (book.best(oppPrice) > 0L && order.price <)
    //  val ordersToRemove
//      for ((priceLevel, bucket) <- oppPrices) {
//         if (volume > 0L && f(order.price, priceLevel)) {
//            if (volume >= bucket.quantity) {
//                volume -= bucket.quantity
//
//            }
//         }
//
//      }
      None
    }

  //}
 // def crossTypeSpread(order: LimitedOrder, oppBook:OrderBook): Option[Trade]  = ???

  def apply(): Behavior[Request] =
    Behaviors.setup[Request](context => new OrderMatching(context))
}

final class OrderMatching(context: ActorContext[Request]) extends AbstractBehavior[Request](context) {

  context.log.info("OrderBook ready")

  import OrderMatching._

  private var limitedOrders: MultiAssetBook[Order.LimitedOrder] = mutable.TreeMap.empty
  private var marketOrders: MultiAssetBook[Order.MarketOrder] = mutable.TreeMap.empty

  //private def doCrossSpread()
  override def onMessage(msg: Request): Behavior[Request] = {
    msg match {
      case AddOrder(timestamp, order, replyTo) => 
      replyTo ! OrderAccepted(LocalDateTime.now(), order.orderId)
      order match {
        case lo@LimitedOrder(symbol, orderId, side, quantity, price) =>
          //limitedOrders.addOne( symbol -> mo)
        case mo@MarketOrder(symbol, orderId, side, quantity) =>
         // marketOrders.addOne()
      }

      this
//      case CancelOrder(timestamp, orderId) =>
//        orderBooks.addOne( symbol -> mo)
//        this
      case _ => this
    }
  }

  override def onSignal: PartialFunction[Signal, Behavior[Request]] = {
    case PostStop =>
      context.log.info("OrderBook stopped")
      this.limitedOrders.clear()
      this.marketOrders.clear()
      this
    case PreRestart =>
      context.log.info("OrderBook starting")
      this.limitedOrders = mutable.TreeMap.empty
      this.marketOrders = mutable.TreeMap.empty
      this
  }
}