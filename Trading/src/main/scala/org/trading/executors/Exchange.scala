package org.trading.executors

import akka.actor.typed._
import akka.actor.typed.scaladsl.Behaviors

object Exchange {
  sealed trait Command
  case object Start extends Command
  case object Stop extends Command

  def apply(): Behavior[Signal] = Behaviors.setup{ ctx =>
    val engine = ctx.spawn(OrderMatching.apply(),"matching-engine")
    Behaviors.receiveMessage{
      PartialFunction.empty
    }

  }
}
