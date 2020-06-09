package com.wzhi.example.workflow

import com.wzhi.example.workflow.Arrows._

import scala.util.{Failure, Try}

trait WorkflowType[F[_]] {
  def toWorkflowItem[A, B](func: A => B): Arrow[F, A, B]
  def toWorkflow2Item[A1, A2, B](func: (A1, A2) => B): Arrow2[F, A1, A2, B]
}

object implicities {
  object OptionFlow extends WorkflowType[Option]{
    implicit def toWorkflowItem[A, B](func: A => B): Arrow[Option, A, B] =
      Arrow[Option, A, B]((input: A) => Try(func(input)).toOption)
    implicit def toWorkflow2Item[A1, A2, B](func: (A1, A2) => B): Arrow2[Option, A1, A2, B] =
      Arrow2[Option, A1, A2, B]((input1: A1, input2: A2) => Try(func(input1, input2)).toOption)
  }

  object ConsoleLogFlow extends WorkflowType[Try] {
    implicit def toWorkflowItem[A, B](func: A => B): Arrow[Try, A, B] =
      Arrow[Try, A, B]((input: A) => Try(func(input)) recoverWith {
        case e: Throwable =>
          println(e)
          Failure(e)
      })
    implicit def toWorkflow2Item[A1, A2, B](func: (A1, A2) => B): Arrow2[Try, A1, A2, B] =
      Arrow2[Try, A1, A2, B]((input1: A1, input2: A2) => Try(func(input1, input2)) recoverWith {
        case e: Throwable =>
          println(e)
          Failure(e)
      })
  }
}
