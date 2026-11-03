package persistence.repositories

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import persistence.models.CustomerRow
import persistence.tables.CustomerTable


@Singleton()
class CustomerRepository @Inject ()(
                                       protected val dbConfigProvider: DatabaseConfigProvider
                                     )(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val customer = CustomerTable.query

  private def categoriesReturning = customer.returning(customer.map(_.id))

  def getAll: Future[Seq[CustomerRow]] =
    db.run(customer.result)

  def insert(cat: CustomerRow): Future[Long] = {
    db.run(categoriesReturning += cat)
  }

  def insert(cats: Seq[CustomerRow]): Future[Long] =
    db.run(categoriesReturning ++= cats)
      .map(_.length.toLong)

  def search(pred: String = "%"): Future[Seq[CustomerRow]] =
    db.run(
      customer.
        filter(_.fullName.
          toLowerCase.
          like(pred.toLowerCase)
        ).result
    )

  def findById(id: Long): Future[Option[CustomerRow]] =
    db.run(customer.filter(_.id === id).result.headOption)

  def delete(id: Long): Future[Int] =
    db.run(customer.filter(_.id === id).delete)

}