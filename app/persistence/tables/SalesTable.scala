package persistence.tables

import org.joda.time.DateTime
import slick.jdbc.MySQLProfile.api._
import persistence.models.SalesRow
import persistence.common.implicits.jodaDateTime

class SalesTable(tag: Tag) extends Table[SalesRow](tag, "SALES"){
  def id = column[Long]("SALE_ID", O.PrimaryKey, O.AutoInc)
  def customerId = column[Long]("CUSTOMER_ID")
  def userId = column[Long]("USER_ID")
  def total = column[BigDecimal]("TOTAL")
  def createdAt = column[DateTime]("CREATED_AT")
  def paidAt = column[DateTime]("PAID_AT")

  def * = (id.?, customerId, userId, total, createdAt, paidAt.?) <>
    (SalesRow.tupled, SalesRow.unapply)

  def customerFK = foreignKey("CUSTOMER_FK", customerId, CustomerTable.query)(_.id)
  def userFK = foreignKey("USER_FK", userId, UserTable.query)(_.id)
}
