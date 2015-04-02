package com.fustigatedcat.metricize.worker.database.core

import com.fustigatedcat.metricize.worker.model._

import scala.slick.driver.MySQLDriver
import MySQLDriver.simple._

object StatisticDAO {

  val statistics = TableQuery[Statistics]

  def createStatistic(agentId : Long, gathered : Long, queryTime : Long, status : String)(implicit session : MySQLDriver.backend.Session) = {
    val statisticId = statistics.returning(statistics.map(_.statisticId)) += Statistic(None, agentId, gathered, queryTime, None, status)
    Statistic(Some(statisticId), agentId, gathered, queryTime, None, status)
  }

}
