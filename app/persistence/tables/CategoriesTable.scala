package persistence.tables

import slick.jdbc.MySQLProfile.api._
import persistence.models.CategoryRow

class CategoriesTable(tag: Tag) extends Table[CategoryRow](tag, "PRODUCTS") {
  def id = column[Long]("CATEGORY_ID", O.PrimaryKey, O.AutoInc)
  def description = column[String]("CATEGORY_DESC")

  def * = (id.?, description) <> (CategoryRow.tupled, CategoryRow.unapply)

}

object CategoriesTable {
  lazy val query = TableQuery[CategoriesTable]
}
