package dao

import javax.inject.{Singleton, Inject}
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import models.{Product, Supplier, Category}
import dao.Schemas.ProductDB
@Singleton()
class ProductDAO @Inject() (
    override protected val dbConfigProvider: DatabaseConfigProvider
)(implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  protected val tables = new ProductDB(profile)
  private val products = tables.products

  private def prodReturning = products returning products.map(_.id)

  def getAll: Future[Seq[Product]] =
    db.run(products.result)

  def insert(product: Product): Future[Long] = {
    db.run(prodReturning += product)
  }

  def insertProducts(prods: Seq[Product]): Future[Long] = {

    db.run(prodReturning ++= prods)
      .map(it => it.length.toLong)
  }
  def search(pred: String = "%"): Future[Seq[Product]] =
    db.run(
      products.filter(_.description.toLowerCase.like(pred.toLowerCase)).result
    )

  def findById(id: Long): Future[Option[Product]] =
    db.run(products.filter(_.id === id).result.headOption)

  def delete(id: Long): Future[Int] =
    db.run(products.filter(_.id === id).delete)

  def getProductsByCategory(id: Long): Future[Seq[(Product, Supplier)]] = {
    val query = for {
      prd <- products
      cat <- prd.categoriesFK if cat.id === id
      sup <- prd.suppliersFK
    } yield (prd, sup)
    db.run(query.result)
  }

  def getProductsBySupplier(
      id: Long
  ): Future[Seq[(Product, Category)]] = {
    val query = for {
      product <- products
      supplier <- product.suppliersFK if supplier.id === id
      category <- product.categoriesFK
    } yield (
      product, category
    )
    db.run(query.result)
  }
}
