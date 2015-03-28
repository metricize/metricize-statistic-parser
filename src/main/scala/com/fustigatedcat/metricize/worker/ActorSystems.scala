package com.fustigatedcat.metricize.worker

import akka.actor.{Props, ActorSystem}
import com.fustigatedcat.metricize.worker.queue.core.EdgeQueue

object ActorSystems {

  val generalActorSystem = ActorSystem("generalActorSystem")

  val edgeQueue = generalActorSystem.actorOf(Props[EdgeQueue])

}
