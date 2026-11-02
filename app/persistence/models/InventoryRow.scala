package persistence.models

import org.joda.time.DateTime

case class InventoryRow(id:Option[Long], createdAt:DateTime)
