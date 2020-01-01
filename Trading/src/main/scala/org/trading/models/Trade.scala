package org.trading.models

import java.util.UUID

object Trade {
  // def from(): Trade
}

final case class Trade(symbol: String,
                       orderId: UUID,
                       executionId: UUID,
                       side: Side,
                       quantity: Int,
                       price: Long)