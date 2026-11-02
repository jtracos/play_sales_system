package persistence.models

import org.joda.time.{DateTime, LocalDate}

case class CustomerRow(
                     id:Option[Long],
                     fullName: String,
                     birthDate:Option[LocalDate],
                     trustWorthy: Boolean,
                     createdAt:Option[DateTime]
                   )
