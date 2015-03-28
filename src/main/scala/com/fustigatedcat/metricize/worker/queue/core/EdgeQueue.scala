package com.fustigatedcat.metricize.worker.queue.core

import akka.camel.{Ack, CamelMessage, Consumer}
import com.fustigatedcat.metricize.worker.Configuration
import org.slf4j.LoggerFactory

class EdgeQueue extends Consumer {

  val logger = LoggerFactory.getLogger(classOf[EdgeQueue])

  override def autoAck = false

  override def endpointUri: String = Configuration.queue.edge.url

  def receive = {
    case msg : CamelMessage => {
      logger.debug(s"Headers: ${msg.headers}")
      logger.debug(msg.bodyAs[String])
      sender() ! Ack
    }
    case _ => println("Unhandled type")
  }

}
