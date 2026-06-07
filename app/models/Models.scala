package  models

import java.time.LocalDateTime

case class Category(id: Option[Long], description: String)

case class  Supplier(id:Option[Long], companyDesc:String, companyName: String)

sealed trait ProductTrait{
    def description: String
    def supplierId:Long
    def categoryId:Long
}
case class Product(id:Option[Long], description: String, supplierId:Long, categoryId:Long, addedTime: LocalDateTime) extends ProductTrait//(description,supplierId,categoryId)

case class ProductInsert(description: String, supplierId:Long, categoryId:Long) extends ProductTrait

case class Inventary(id:Option[Long], createdAt:String)

case class Stock(
    stockId:Long, inventaryId:Long, prductId: Long,purchasePrice: BigDecimal,
    soldPrice:BigDecimal, suppliedQty:Long, stockQty:Long, createdAt:LocalDateTime,
    UpdatedAt:Option[LocalDateTime]
    )
