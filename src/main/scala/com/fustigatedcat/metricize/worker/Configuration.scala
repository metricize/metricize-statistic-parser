package com.fustigatedcat.metricize.worker

import com.typesafe.config.ConfigFactory

/**
 * Created by jbackfield on 3/24/15.
 */
object Configuration {

  val config = ConfigFactory.load()

  object database {
    object core {
      object jdbc {
        val url = config.getString("database.core.jdbc.url")
        val driver = config.getString("database.core.jdbc.driver")
      }
      val username = config.getString("database.core.username")
      val password = config.getString("database.core.password")
    }
  }

  object queue {
    object edge {
      val url = config.getString("queue.edge.url")
    }
  }

}
