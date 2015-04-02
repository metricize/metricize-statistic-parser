package com.fustigatedcat.metricize.worker.model

import slick.driver.MySQLDriver.simple._

case class Record(record_id : Option[Long], statistic_id : Long, duplicate_id: Option[Long])

class Records(tag : Tag) extends Table[Record](tag, "Record") {
  def recordId = column[Long]("statistic_id", O.PrimaryKey, O.AutoInc)
  def statisticId = column[Long]("statistic_id")
  def duplicateId = column[Long]("duplicate_id")

  def * = (recordId.?, statisticId, duplicateId.?) <> (Record.tupled, Record.unapply)
}
