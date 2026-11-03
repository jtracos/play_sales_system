package persistence.repositories

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import persistence.models.ProductRow
import persistence.tables.ProductTable

@Singleton()
class ProductRepository @Inject() (
                             override protected val dbConfigProvider: DatabaseConfigProvider
                           )(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._


  private val products = ProductTable.query

  private def prodReturning = products returning products.map(_.id)

  def getAll: Future[Seq[ProductRow]] =
    db.run(products.result)

  def insert(product: ProductRow): Future[Long] = {
    db.run(prodReturning += product)
  }

  def insert(products: Seq[ProductRow]): Future[Long] = {
    db.run(prodReturning ++= products)
      .map(it => it.length.toLong)
  }
  def search(pred: String = "%"): Future[Seq[ProductRow]] =
    db.run(
      products.filter(_.description.toLowerCase.like(pred.toLowerCase)).result
    )

  def find(id: Long): Future[Option[ProductRow]] = {
    db.run(products.filter(_.id === id).result.headOption)
  }

  def find(ids:Seq[Long]):Future[Seq[ProductRow]]= {
    db.run(products.filter(prod => prod.id.inSet(ids)).result)
  }

  def delete(id: Long): Future[Int] =
    db.run(products.filter(_.id === id).delete)

}
