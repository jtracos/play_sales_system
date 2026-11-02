package persistence.common

import org.joda.time.DateTime
import org.joda.time.DateTimeZone.UTC
import java.sql.Timestamp
import slick.jdbc.MySQLProfile.api._

object implicits {
  implicit val jodaDateTime: BaseColumnType[DateTime] =
    MappedColumnType.base[DateTime, Timestamp](
      dt => new Timestamp(dt.getMillis),
      ts => new DateTime(ts.getTime, UTC)
    )
}
