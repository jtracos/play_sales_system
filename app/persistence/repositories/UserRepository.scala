package persistence.repositories

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import persistence.models.UserRow
import persistence.tables.UserTable


@Singleton()
class UserRepository @Inject ()(
                                     protected val dbConfigProvider: DatabaseConfigProvider
                                   )(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val user = UserTable.query

  private def userReturning = user.returning(user.map(_.id))

  def getAll: Future[Seq[UserRow]] =
    db.run(user.result)

  def insert(cat: UserRow): Future[Long] = {
    db.run(userReturning += cat)
  }

  def insert(users: Seq[UserRow]): Future[Long] =
    db.run(userReturning ++= users)
      .map(_.length.toLong)

  def search(pred: String = "%"): Future[Seq[UserRow]] =
    db.run(
      user.
        filter(_.fullName.
          toLowerCase.
          like(pred.toLowerCase)
        ).result
    )

  def findById(id: Long): Future[Option[UserRow]] =
    db.run(user.filter(_.id === id).result.headOption)

  def delete(id: Long): Future[Int] =
    db.run(user.filter(_.id === id).delete)

}