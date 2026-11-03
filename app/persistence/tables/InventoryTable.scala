package persistence.tables

import org.joda.time.DateTime
import slick.jdbc.MySQLProfile.api._
import persistence.models.InventoryRow
import persistence.common.implicits.jodaDateTime

class InventoryTable(tag: Tag) extends Table[InventoryRow](tag, "INVENTORY"){
  def id = column[Long]("INVENTORY_ID", O.PrimaryKey, O.AutoInc)
  def createdAt = column[DateTime]("CREATED_AT")
  def description = column[String]("DESCRIPTION", O.SqlType("VARCHAR(40)"))
  def source = column[String]("SOURCE", O.SqlType("VARCHAR(40)"))

  def * = (id.?, createdAt.?, description.?, source.?) <>
    (InventoryRow.tupled, InventoryRow.unapply)

}

object InventoryTable {
  lazy val query = TableQuery[InventoryTable]
}
