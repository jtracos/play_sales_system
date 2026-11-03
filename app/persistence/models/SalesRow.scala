package persistence.models

import org.joda.time.DateTime

case class SalesRow (
    saleId: Option[Long],
    customerId: Long,
    userId: Long,
    total: BigDecimal,
    createdAt: DateTime,
    paidAt: Option[DateTime]
                    )
