package com.zhi.resources
import java.util.concurrent.Executors

import cats.effect.ExitCase.{Canceled, Completed, Error}
import cats.effect.IO

import scala.io.BufferedSource

object ResourceManage {
  def readClosableSource[A](source: => BufferedSource)(processData: List[String] => A): IO[A] =
    IO {
      source
    }.bracketCase { in =>
      IO {
        processData(in.getLines().toList)
      }
    } {
      case (_, Completed | Error(_)) =>
        // Do nothing
        IO.unit
      case (in, Canceled) =>
        IO(in.close())
    }

  implicit class SourceRead(source: => BufferedSource) {
    def processAs[A](processData: List[String] => A): IO[A] = readClosableSource(source)(processData)
  }
}

