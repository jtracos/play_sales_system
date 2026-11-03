package persistence.repositories

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import persistence.models.InventoryRow
import persistence.tables.InventoryTable

@Singleton()
class InventoryRepository @Inject() (
                                    override protected val dbConfigProvider: DatabaseConfigProvider
                                  )(implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._


  private val inventory = InventoryTable.query

  private def prodReturning = inventory returning inventory.map(_.id)

  def getAll: Future[Seq[InventoryRow]] =
    db.run(inventory.result)

  def insert(product: InventoryRow): Future[Long] = {
    db.run(prodReturning += product)
  }

  def insert(inventory: Seq[InventoryRow]): Future[Long] = {
    db.run(prodReturning ++= inventory)
      .map(it => it.length.toLong)
  }
  def search(pred: String = "%"): Future[Seq[InventoryRow]] =
    db.run(
      inventory.filter(_.description.toLowerCase.like(pred.toLowerCase)).result
    )

  def find(id: Long): Future[Option[InventoryRow]] = {
    db.run(inventory.filter(_.id === id).result.headOption)
  }

  def find(ids:Seq[Long]):Future[Seq[InventoryRow]]= {
    db.run(inventory.filter(prod => prod.id.inSet(ids)).result)
  }

  def delete(id: Long): Future[Int] =
    db.run(inventory.filter(_.id === id).delete)

}
