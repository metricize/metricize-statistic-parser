package com.fustigatedcat.metricize.worker.queue.core

import akka.camel.{Ack, CamelMessage, Consumer}
import com.fustigatedcat.metricize.worker.Configuration

class EdgeQueue extends Consumer {

  override def autoAck = false

  override def endpointUri: String = Configuration.queue.edge.url

  def receive = {
    case msg : CamelMessage => {
      println(msg.bodyAs[String])
      sender() ! Ack
    }
    case _ => println("Unhandled type")
  }

}
