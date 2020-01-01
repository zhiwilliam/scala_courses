package org.trading

import akka.{Done, NotUsed, actor}
import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import zio.Exit.{Failure, Success}
import zio.{Managed, Queue, Runtime, Task, UIO, ZEnv, ZIO}

import scala.concurrent.ExecutionContextExecutor

object Main extends App {
//   sealed trait Message
//   final case class StartFailed(cause: Throwable) extends Message
// // private final case class Started(binding: ServerBinding) extends Message
//   case object Stop extends Message
//
//  private val actorSystem: Managed[Throwable, ActorSystem[Message]] =
//    Managed.make(Task(ActorSystem(Main(), "Exchange-Demo")))(sys => UIO(sys.terminate()))
//
//  private val exchangeServer: ZIO[ActorSystem[Done], Throwable, Unit] = ???
//
//  def run(args: List[String]) = exchangeServer.fold(_ => 1, _ => 0)
//
//  private def apply(): Behavior[Message] = Behaviors.setup { ctx =>
//    // http doesn't know about akka typed so create untyped system/materializer
//    implicit val system = ctx.system
//    implicit val untypedSystem: akka.actor.ActorSystem = system.toClassic
//    implicit val ec: ExecutionContextExecutor = ctx.system.executionContext
//
//
//  }
//
//  //ZIO.runtime[ActorSystem[Done], Tho]

}
