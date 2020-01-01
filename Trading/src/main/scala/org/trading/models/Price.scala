package org.trading.models

import enumeratum._

sealed trait Price extends EnumEntry

object Price extends Enum[Price] {
  val values = findValues

   case object Ask extends Price

   case object Bid extends Price

//  /*!< Last price. */
//  case object Last extends Price
//
//  /*!< Close price. */
//  case object Close extends Price
//
//  /*!< Mid price, calculated as the arithmetic */
//  case object Mid extends Price

  type Level = Long

  implicit class PriceOps(val price: Price) extends AnyVal{
    def unary_!(): Price = price match {
      case Ask => Bid
      case Bid => Ask
    }
  }


}

