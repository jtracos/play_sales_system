package dao

import javax.inject.{Singleton, Inject}
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import models.Category
import dao.Schemas.ProductDB

@Singleton()
class CategoriesDAO @Inject () (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  protected val tables = new ProductDB(profile)
  private val categories = tables.categories

  private def categoriesReturning = categories.returning(categories.map(_.id))

  def getAll: Future[Seq[Category]] =
    db.run(categories.result)

  def insert(cat: Category): Future[Long] = {
    db.run(categoriesReturning += cat)
  }

  def insertProducts(cats: Seq[Category]): Future[Long] =
    db.run(categoriesReturning ++= cats)
      .map(it => it.length.toLong)

  def search(pred: String = "%"): Future[Seq[Category]] =
    db.run(
      categories.filter(_.description.toLowerCase.like(pred.toLowerCase)).result
    )

  def findById(id: Long): Future[Option[Category]] =
    db.run(categories.filter(_.id === id).result.headOption)

  def delete(id: Long): Future[Int] =
    db.run(categories.filter(_.id === id).delete)

}
