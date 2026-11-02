package persistence.models

import org.joda.time.DateTime

case class ProductRow(id:Option[Long], description: String, supplierId:Long, categoryId:Long, addedAt: DateTime)
