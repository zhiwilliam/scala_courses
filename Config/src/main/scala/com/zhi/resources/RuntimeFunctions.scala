package com.zhi.resources

import java.util.concurrent.{ExecutorService, Executors}

import cats.effect.IO

import scala.concurrent.ExecutionContext

object RuntimeFunctions {
  private val cachedExecutor: ExecutorService = Executors.newCachedThreadPool()
  private val BlockingFileIO: ExecutionContext = ExecutionContext.fromExecutor(cachedExecutor)

  private implicit val mainExecuteContext = IO.contextShift(ExecutionContext.global)
  private implicit val timer = IO.timer(ExecutionContext.global)

  def shutdown(): Unit = {
    cachedExecutor.shutdown()
  }

  def unsafeBlockingRunSync[A](operation: IO[A]): A = {
    val runnable = for {
      _     <- IO.shift(BlockingFileIO)
      result  <- operation
      _     <- IO.shift
    } yield result
    runnable.unsafeRunSync()
  }

  def attemptBlockingRunSync[A](operation: IO[A]): Any = {
    val runnable = for {
      _     <- IO.shift(BlockingFileIO)
      result  <- operation
      _     <- IO.shift
    } yield result
    runnable.attempt.flatMap {
      case Left(exception) => IO.cancelBoundary // or log and cancel
      case Right(value) => IO(value)
    }.unsafeRunSync()
  }

  implicit class IOOps[A](operation: IO[A]) {
    def unsafeBlockingRunSync: A = RuntimeFunctions.unsafeBlockingRunSync(operation)
    def attemptBlockingRunSync: Any = RuntimeFunctions.attemptBlockingRunSync(operation)
  }
}
