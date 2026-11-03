package persistence.tables

import slick.jdbc.MySQLProfile.api._
import org.joda.time.DateTime
import  persistence.models.ProductRow
import persistence.common.implicits.jodaDateTime

class ProductTable(tag: Tag) extends Table[ProductRow](tag, "PRODUCTS") {
  def id = column[Long]("PRODUCT_ID", O.PrimaryKey, O.AutoInc)
  def description = column[String]("PRODUCT_DESC")
  def supplierId = column[Long]("SUPPLIER_ID")
  def categoryId = column[Long]("CATEGORY_ID")
  def addedTime = column[DateTime]("ADDED_AT")

  def * = (id.?, description, supplierId, categoryId, addedTime) <> (
    ProductRow.tupled,
    ProductRow.unapply
  )

  def suppliersFK =
    foreignKey("SUPPLIERS_FK", supplierId, SupplierTable.query)(_.id)
  def categoriesFK =
    foreignKey("SUPPLIERS_FK", supplierId, CategoriesTable.query)(_.id)

}

object ProductTable {
  lazy val query = TableQuery[ProductTable]
}
