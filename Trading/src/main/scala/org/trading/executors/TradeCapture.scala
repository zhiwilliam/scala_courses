package org.trading.executors

import akka.actor.typed._
import akka.actor.typed.scaladsl._
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior}
import org.trading.messages.TradeCapture._


object TradeCapture {

  val commandHandler: (State, Command) => Effect[Event, State] = {
    case (_, command) =>  Effect.persist(Added(data))
    case (_, _) => Effect.persist(Cleared)
  }

  val eventHandler: (State, Event) => State = {
    case (state, Added(data)) => state.copy((data :: state.history).take(5))
    case (_,Cleared)     => State(Nil)
    }


  def apply(id: String): Behavior[Command] =
    EventSourcedBehavior[Command, Event, State](
      persistenceId = PersistenceId.ofUniqueId(id),
      emptyState = State(Nil),
      commandHandler = commandHandler,
      eventHandler = eventHandler).onPersistFailure(
       SupervisorStrategy.restartWithBackoff(minBackoff = 10.seconds, maxBackoff = 60.seconds, randomFactor = 0.1))

      )
    )
}
