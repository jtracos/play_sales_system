package persistence.common

import org.joda.time.{DateTime,LocalDate}
import org.joda.time.DateTimeZone.UTC
import java.sql.{Timestamp,Date}
import slick.jdbc.MySQLProfile.api._

object implicits {
  implicit val jodaDateTime: BaseColumnType[DateTime] =
    MappedColumnType.base[DateTime, Timestamp](
      dt => new Timestamp(dt.getMillis),
      ts => new DateTime(ts.getTime, UTC)
    )
  implicit val jodaDate: BaseColumnType[LocalDate] =
    MappedColumnType.base[LocalDate, Date](
      jdate => new Date(jdate.toDate.getTime),
      date => new LocalDate(date.getTime)
    )
}
