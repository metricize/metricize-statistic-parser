package com.fustigatedcat.metricize.worker.database.core

import com.fustigatedcat.metricize.worker.model._
import org.json4s.JsonAST.{JString, JObject, JValue}

import scala.slick.driver.MySQLDriver
import scala.slick.driver.MySQLDriver.simple._

object RecordDAO {

  val recordedFields = TableQuery[RecordedFields]

  val recordedDatas = TableQuery[RecordedDatas]

  val records = TableQuery[Records]

  def createRecordData(recordId : Long, fields : List[(String, JValue)], fieldMappings : Map[String, Long] = Map())(implicit session : MySQLDriver.backend.Session) : Unit = fields match {
    case List() => Unit
    case (fieldName, JString(value)) :: remaining => {
      val newFieldMappings = fieldMappings.get(fieldName) match {
        case Some(id) => fieldMappings
        case _ => fieldMappings + (fieldName -> createRecordedField(fieldName))
      }
      recordedDatas += RecordedData(recordId, newFieldMappings(fieldName), value, None)
      createRecordData(recordId, remaining, newFieldMappings)
    }
  }

  def createRecord(statisticId : Long, fields : List[(String, JValue)])(implicit session : MySQLDriver.backend.Session) = {
    val recordId = records.returning(records.map(_.recordId)) += Record(None, statisticId, None)
    createRecordData(recordId, fields)
    Record(Some(recordId), statisticId, None)
  }

  def createRecordedField(field : String)(implicit session : MySQLDriver.backend.Session) : Long = {
    recordedFields.filter(_.name === field).firstOption match {
      case Some(recordedField) => recordedField.field_id.get
      case _ => {
        recordedFields.returning(recordedFields.map(_.fieldId)) += RecordedField(None, field)
      }
    }
  }

}
