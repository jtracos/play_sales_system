package persistence.models

import org.joda.time.DateTime

case class SaleDetailsRow (
    saleDetailId: Option[Long],
    saleId: Long,
    createdAt: DateTime,
    price: BigDecimal,
    quantity: Long
                          )
