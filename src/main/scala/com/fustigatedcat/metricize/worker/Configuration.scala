package com.fustigatedcat.metricize.worker

import com.typesafe.config.ConfigFactory

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
    object customer {
      object jdbc {
        val template = config.getString("database.customer.jdbc.template")
        val driver = config.getString("database.customer.jdbc.driver")
      }
    }
  }

  object queue {
    object edge {
      val url = config.getString("queue.edge.url")
    }
  }

}
