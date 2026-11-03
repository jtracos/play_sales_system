package persistence.repositories


import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import persistence.models.SupplierRow
import persistence.tables.SupplierTable

@Singleton()
class SuppliersRepository @Inject() (
                               protected val dbConfigProvider: DatabaseConfigProvider
                             )(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val suppliers = SupplierTable.query

  private def suppliersReturning = suppliers.returning(suppliers.map(_.id))

  def getAll: Future[Seq[SupplierRow]] =
    db.run(suppliers.result)

  def create(cat: SupplierRow): Future[Long] = {
    db.run(suppliersReturning += cat)
  }

  def insertSuppliers(cats: Seq[SupplierRow]): Future[Option[Int]] =
    db.run(suppliers ++= cats)

  def search(pred: String = "%"):Future[Seq[SupplierRow]] =
    db.run(
      suppliers.filter(_.companyName.toLowerCase.like(pred.toLowerCase)).result
    )

  def find(id: Long): Future[Option[SupplierRow]] =
    db.run(suppliers.filter(_.id === id).result.headOption)

  def delete(id: Long):Future[Int] =
    db.run(suppliers.filter(_.id === id).delete)
}

