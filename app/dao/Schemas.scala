package dao

import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import models.{Category, Supplier, Product}
import org.joda.time.DateTime
import org.joda.time.DateTimeZone.UTC
import java.sql.Timestamp

object  Schemas {

  trait Profile {
    val profile: JdbcProfile
  }

  trait ProductTables {
    this: Profile =>

    import profile.api._

    

    implicit val jodaDateTime: profile.BaseColumnType[DateTime] =
      MappedColumnType.base[DateTime, Timestamp](
        dt => new Timestamp(dt.getMillis),
        ts => new DateTime(ts.getTime, UTC)
      )

    class Suppliers(tag: Tag) extends Table[Supplier](tag, "SUPPLIERS") {
      def id = column[Long]("SUPPLIER_ID", O.PrimaryKey, O.AutoInc)
      def companyDesc = column[String]("COMPANY_DESC")
      def companyName = column[String]("COMPANY_NAME")

      def * = (id.?, companyDesc, companyName) <> (
        Supplier.tupled,
        Supplier.unapply
      )
    }

    class Categories(tag: Tag) extends Table[Category](tag, "PRODUCTS") {
      def id = column[Long]("CATEGORY_ID", O.PrimaryKey, O.AutoInc)
      def description = column[String]("CATEGORY_DESC")

      def * = (id.?, description) <> (Category.tupled, Category.unapply)

    }

    class Products(tag: Tag) extends Table[Product](tag, "PRODUCTS") {
      def id = column[Long]("PRODUCT_ID", O.PrimaryKey, O.AutoInc)
      def description = column[String]("PRODUCT_DESC")
      def supplierId = column[Long]("SUPPLIER_ID")
      def categoryId = column[Long]("CATEGORY_ID")
      def addedTime = column[DateTime]("ADDED_AT")

      def * = (id.?, description, supplierId, categoryId, addedTime) <> (
        Product.tupled,
        Product.unapply
      )

      def suppliersFK = foreignKey("SUPPLIERS_FK", supplierId, suppliers)(_.id)
      def categoriesFK = foreignKey("SUPPLIERS_FK", supplierId, categories)(_.id)

    }


    lazy val suppliers = TableQuery[Suppliers]
    lazy val categories = TableQuery[Categories]
    lazy val products = TableQuery[Products]

  }

  class ProductDB(val profile: JdbcProfile) extends ProductTables with Profile

}
