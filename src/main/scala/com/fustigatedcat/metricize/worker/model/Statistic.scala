package com.fustigatedcat.metricize.worker.model

import slick.driver.MySQLDriver.simple._

case class Statistic(statistic_id : Option[Long], agent_id : Long, gathered : Long, query_time : Long, duplicate_id : Option[Long], status : String)

class Statistics(tag : Tag) extends Table[Statistic](tag, "Statistic") {
  def statisticId = column[Long]("statistic_id", O.PrimaryKey, O.AutoInc)
  def agentId = column[Long]("agent_id")
  def gathered = column[Long]("gathered")
  def query_time = column[Long]("query_time")
  def duplicateId = column[Long]("duplicate_id")
  def status = column[String]("status")

  def * = (statisticId.?, agentId, gathered, query_time, duplicateId.?, status) <> (Statistic.tupled, Statistic.unapply)
}