package org.trading.models

import enumeratum._

sealed trait Side extends EnumEntry

object Side extends Enum[Side] {
  val values = findValues

  case object Buy extends Side

  case object Sell extends Side

}

