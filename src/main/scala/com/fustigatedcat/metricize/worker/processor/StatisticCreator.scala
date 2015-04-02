package com.fustigatedcat.metricize.worker.processor

import com.fustigatedcat.metricize.worker.database.core.{RecordDAO, AgentDAO, DB, StatisticDAO}
import com.fustigatedcat.metricize.worker.model.Statistic
import org.json4s.DefaultFormats
import org.json4s.JsonAST.{JObject, JArray}
import org.json4s.native.parseJsonOpt
import org.slf4j.LoggerFactory

object StatisticCreator {

  val logger = LoggerFactory.getLogger(StatisticCreator.getClass)

  implicit val formats = DefaultFormats

  def createStatistic(headers : Map[String, Any], body : String) : Option[Statistic] = parseJsonOpt(body) match {
    case Some(json) => try {
      val agentId = headers("agent-id").toString.toLong
      DB.getCustomerDB(AgentDAO.getAgentById(agentId).customerId).withTransaction { implicit session =>
        val stat = StatisticDAO.createStatistic(
          agentId,
          headers("start-time").toString.toLong,
          headers("time").toString.toLong,
          headers("status").toString
        )
        json.extract[JArray].arr.foreach { case JObject(fields) =>
          RecordDAO.createRecord(stat.statistic_id.get, fields)
        }
        Some(stat)
      }
    } catch {
      case t : Throwable => {
        logger.error("Failed to create statistic", t)
        None
      }
    }
    case _ => {
      logger.error("Failed to parse body {}", body)
      None
    }
  }

}
