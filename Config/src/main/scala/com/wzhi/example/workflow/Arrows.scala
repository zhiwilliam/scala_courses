package com.wzhi.example.workflow

import cats.FlatMap
import cats.implicits._

import scala.language.implicitConversions

object Arrows {
  final case class MergeWF[F[_], A, B, C, D](first: Arrow[F, A, B], second: Arrow[F, C, D]) {
    def ~> [Z](k: Arrow2[F, B, D, Z])(implicit F: FlatMap[F]): Arrow2[F, A, C, Z] = {
      Arrow2[F, A, C, Z]((a, c) => first.run(a).flatMap(b => second.run(c).flatMap(d=> k.run(b, d))))
    }
  }

  final case class Arrow[F[_], A, B](run: A => F[B]) {
    def ~> [C](k: Arrow[F, B, C])(implicit F: FlatMap[F]): Arrow[F, A, C] =
      Arrow[F, A, C](a => run(a).flatMap(k.run))

    def +> [C, D](k: Arrow[F, C, D]): MergeWF[F, A, B, C, D] = MergeWF(this, k)

    def ≈> [K[_], C](k: Arrow[K, B, C])(implicit K: FlatMap[K], converter: F[B] => K[B]): Arrow[K, A, C] =
      Arrow[K, A, C](a => converter(run(a)).flatMap(b => k.run(b)))
  }

  final case class Arrow2[F[_], A, B, C](run: (A, B) => F[C]) {
    def ~> [Z](k: Arrow[F, C, Z])(implicit F: FlatMap[F]): Arrow2[F, A, B, Z] =
      Arrow2[F, A, B, Z]((a, b) => run(a, b).flatMap(x => k.run(x)))
  }
}