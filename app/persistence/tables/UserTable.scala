package persistence.tables

import org.joda.time.{LocalDate, DateTime}
import slick.jdbc.MySQLProfile.api._
import persistence.models.UserRow
import persistence.common.implicits._

class UserTable(tag:Tag) extends Table[UserRow](tag,"CUSTOMERS"){
  def id = column[Long]("USER_ID", O.PrimaryKey, O.AutoInc)
  def fullName = column[String]("FULL_NAME")
  def birthDate = column[LocalDate]("BIRTH_DATE")
  def createdAt = column[DateTime]("UPDATED_AT")

  def * = (id.?, fullName, birthDate.?, createdAt.?) <>
    (UserRow.tupled, UserRow.unapply)
}

object UserTable {
  lazy val query = TableQuery[UserTable]
}
