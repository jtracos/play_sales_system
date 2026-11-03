package persistence.models

import org.joda.time.DateTime

case class InventoryRow(
                         id:Option[Long],
                         createdAt:Option[DateTime],
                         description: Option[String],
                         source: Option[String]
                       )
