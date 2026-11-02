package persistence.models

import org.joda.time.DateTime

case class UserRow (
                     id:Option[Long],
                     fullName: String,
                     birthDate:DateTime,
                     updatedAt: DateTime
                   )
