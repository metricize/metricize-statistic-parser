package com.fustigatedcat.metricize.worker.queue.core

import akka.actor.Status.Failure
import akka.camel.{Ack, CamelMessage, Consumer}
import com.fustigatedcat.metricize.worker.Configuration
import com.fustigatedcat.metricize.worker.processor.StatisticCreator
import org.slf4j.LoggerFactory

class EdgeQueue extends Consumer {

  val logger = LoggerFactory.getLogger(classOf[EdgeQueue])

  override def autoAck = false

  override def endpointUri: String = Configuration.queue.edge.url

  def receive = {
    case msg : CamelMessage => {
      StatisticCreator.createStatistic(msg.headers, msg.bodyAs[String]) match {
        case Some(stat) => {
          logger.debug(s"Created statistic [${stat.statistic_id}}]")
          sender() ! Ack
        }
        case _ => sender() ! Failure(new RuntimeException("Failed to create statistic"))
      }
    }
    case _ => println("Unhandled type")
  }

}
