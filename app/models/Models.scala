package  models

import org.joda.time.DateTime

case class Category(id: Option[Long], description: String)

case class  Supplier(id:Option[Long], companyDesc:String, companyName: String)

sealed trait ProductTrait{
    def description: String
    def supplierId:Long
    def categoryId:Long
}
case class Product(id:Option[Long], description: String, supplierId:Long, categoryId:Long, addedAt: DateTime) extends ProductTrait//(description,supplierId,categoryId)

case class ProductInsert(description: String, supplierId:Long, categoryId:Long) extends ProductTrait

case class Inventary(id:Option[Long], createdAt:DateTime)

case class Stock(
    stockId:Long, inventaryId:Long, prductId: Long,purchasePrice: BigDecimal,
    soldPrice:BigDecimal, suppliedQty:Long, stockQty:Long, createdAt:DateTime,
    updatedAt:Option[DateTime]
    )
