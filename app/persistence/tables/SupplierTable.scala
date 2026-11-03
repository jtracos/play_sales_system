package persistence.tables


import  slick.jdbc.MySQLProfile.api._

import persistence.models.SupplierRow


class SupplierTable(tag: Tag) extends Table[SupplierRow](tag, "SUPPLIERS"){
  def id = column[Long]("SUPPLIER_ID", O.PrimaryKey, O.AutoInc)

  def companyDesc = column[String]("COMPANY_DESC")

  def companyName = column[String]("COMPANY_NAME")

  def * =
    (id.?, companyDesc, companyName) <> (SupplierRow.tupled, SupplierRow.unapply)
}

object SupplierTable{
  lazy val query = TableQuery[SupplierTable]
  }
