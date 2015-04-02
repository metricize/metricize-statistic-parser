package com.fustigatedcat.metricize.worker.model

import slick.driver.MySQLDriver.simple._

case class RecordedData(record_id : Long, field_id : Long, value : String, duplicate_id: Option[Long])

class RecordedDatas(tag : Tag) extends Table[RecordedData](tag, "RecordedData") {
  def recordId = column[Long]("record_id", O.PrimaryKey)
  def fieldId = column[Long]("field_id", O.PrimaryKey)
  def value = column[String]("value")
  def duplicateId = column[Long]("duplicate_id")

  def * = (recordId, fieldId, value, duplicateId.?) <> (RecordedData.tupled, RecordedData.unapply)
}

