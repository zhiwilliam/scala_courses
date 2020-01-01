package org.trading

import java.time.{LocalDateTime, ZonedDateTime}
import java.util.UUID

import akka.actor.typed.{ActorRef, Behavior}
import org.trading.models.{Order, Trade}

package object messages {
  sealed trait Request
  sealed trait Response
  sealed trait Event

  final case class AddOrder(timestamp: LocalDateTime, order: Order.Type, replyTo: ActorRef[Response]) extends Request
  final case class CancelOrder(timestamp: LocalDateTime, orderId: UUID, replyTo: ActorRef[Response]) extends Request

  final case class OrderAccepted(timestamp: LocalDateTime, orderId: UUID) extends Response
  final case class OrderExecuted(timestamp:  LocalDateTime, orderId: UUID) extends Response

  final case class TradeExecuted(timestamp: LocalDateTime, trade: Trade) extends Event
  final case class OrderRemoved(timestamp: LocalDateTime, orderId: UUID) extends Event
  final case class OrderCancelled(timestamp: LocalDateTime, orderId: UUID) extends Event

}
