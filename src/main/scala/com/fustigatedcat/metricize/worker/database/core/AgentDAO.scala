package com.fustigatedcat.metricize.worker.database.core

import com.fustigatedcat.metricize.worker.model.Agents

import scala.slick.driver.MySQLDriver.simple._

object AgentDAO {

  val agents = TableQuery[Agents]

  def getAgentById(agentId : Long) = DB.db.withSession { implicit session =>
    agents.filter(_.id === agentId).firstOption match {
      case Some(agent) => agent
      case _ => throw new IllegalArgumentException("Cannot find agent")
    }
  }

}
