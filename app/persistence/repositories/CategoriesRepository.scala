package persistence.repositories

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import persistence.models.CategoryRow
import persistence.tables.CategoriesTable

@Singleton()
class CategoriesRepository @Inject ()(
                                       protected val dbConfigProvider: DatabaseConfigProvider
                                     )(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val categories = CategoriesTable.query

  private def categoriesReturning = categories.returning(categories.map(_.id))

  def getAll: Future[Seq[CategoryRow]] =
    db.run(categories.result)

  def insert(cat: CategoryRow): Future[Long] = {
    db.run(categoriesReturning += cat)
  }

  def insertProducts(cats: Seq[CategoryRow]): Future[Long] =
    db.run(categoriesReturning ++= cats)
      .map(it => it.length.toLong)

  def search(pred: String = "%"): Future[Seq[CategoryRow]] =
    db.run(
      categories.filter(_.description.toLowerCase.like(pred.toLowerCase)).result
    )

  def findById(id: Long): Future[Option[CategoryRow]] =
    db.run(categories.filter(_.id === id).result.headOption)

  def delete(id: Long): Future[Int] =
    db.run(categories.filter(_.id === id).delete)

}