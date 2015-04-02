package com.fustigatedcat.metricize.worker.model

import slick.driver.MySQLDriver.simple._

case class RecordedField(field_id : Option[Long], name : String)

class RecordedFields(tag : Tag) extends Table[RecordedField](tag, "RecordedField") {
  def fieldId = column[Long]("field_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")

  def * = (fieldId.?, name) <> (RecordedField.tupled, RecordedField.unapply)
}

