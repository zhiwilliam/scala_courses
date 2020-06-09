package com.wzhi.example.workflow

import com.wzhi.example.workflow.Arrows._

object Decorators {
  def consoleLogTime[A](input: A): A = {
    println(input)
    input
  }

  def consoleLogTime2[A1, A2](input1: A1, input2: A2): (A1, A2) = {
    println(input1 + "," + input2)
    (input1, input2)
  }

  def skip[A](input: A): A = input
  def skip2[A1, A2](input1: A1, input2: A2): (A1, A2) = (input1, input2)

  def createDecorator[A, B, F[_]](before: A => A, after: B => B, flow: WorkflowType[F])(func: A => B): Arrow[F, A, B] = {
    val decoratedFunc = (input: A) => after(func(before(input)))
    flow.toWorkflowItem(decoratedFunc)
  }

  def createDecorator2[A1, A2, B, F[_]](before: (A1, A2) => (A1, A2), after: B => B, flow: WorkflowType[F])(func: (A1, A2) => B): Arrow2[F, A1, A2, B] = {
    val decoratedFunc = (input1: A1, input2: A2) => {
      val res = before(input1, input2)
      after(func(res._1, res._2))
    }
    flow.toWorkflow2Item(decoratedFunc)
  }
}
