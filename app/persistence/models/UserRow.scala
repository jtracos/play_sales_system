package persistence.models

import org.joda.time.{DateTime, LocalDate}

case class UserRow (
                     id:Option[Long],
                     fullName:String,
                     birthDate:Option[LocalDate],
                     updatedAt:Option[DateTime]
                   )
