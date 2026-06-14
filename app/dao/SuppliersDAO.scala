package dao

import javax.inject.{Singleton, Inject}
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import models.Supplier
import dao.Schemas.ProductDB

@Singleton()
class SuppliersDAO @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  protected val tables = new ProductDB(profile)
  private val suppliers = tables.suppliers

  private def suppliersReturning = suppliers.returning(suppliers.map(_.id))

  def all =
    db.run(suppliers.result)

  def create(cat: Supplier): Future[Long] = {
    db.run(suppliersReturning += cat)
  }

  def insertProducts(cats: Seq[Supplier]): Future[Option[Int]] =
    db.run(suppliers ++= cats)

  def search(pred: String = "%") =
    db.run(
      suppliers.filter(_.companyName.toLowerCase.like(pred.toLowerCase)).result
    )

  def findById(id: Long): Future[Option[Supplier]] =
    db.run(suppliers.filter(_.id === id).result.headOption)

  def delete(id: Long) =
    db.run(suppliers.filter(_.id === id).delete)
}
