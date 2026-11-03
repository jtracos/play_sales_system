package persistence.tables

import org.joda.time.{LocalDate, DateTime}
import slick.jdbc.MySQLProfile.api._
import persistence.models.CustomerRow
import persistence.common.implicits._

class CustomerTable(tag:Tag) extends Table[CustomerRow](tag,"CUSTOMERS"){
  def id = column[Long]("CUSTOMER_ID", O.PrimaryKey, O.AutoInc)
  def fullName = column[String]("FULL_NAME")
  def birthDate = column[LocalDate]("BIRTH_DATE")
  def valuableCustomer = column[Boolean]("TRUST_WORTH")
  def createdAt = column[DateTime]("UPDATED_AT")

  def * = (id.?, fullName, birthDate.?, valuableCustomer, createdAt.?) <>
    (CustomerRow.tupled, CustomerRow.unapply)
}

object CustomerTable {
  lazy val query = TableQuery[CustomerTable]
}
