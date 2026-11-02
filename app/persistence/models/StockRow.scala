package persistence.models

import org.joda.time.DateTime

case class StockRow (
    stockId: Option[Long],
    inventoryId: Long,
    productId: Long,
    purchasePrice: BigDecimal,
    soldPrice: BigDecimal,
    suppliedQuantity: Int,
    stockQuantity: Int,
    createdAt: DateTime,
    updatedAt: Option[DateTime],
    active: Boolean = true
                    )


