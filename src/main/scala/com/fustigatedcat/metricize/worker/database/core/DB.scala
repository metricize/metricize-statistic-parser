package com.fustigatedcat.metricize.worker.database.core

import com.fustigatedcat.metricize.worker.Configuration

import scala.slick.driver.MySQLDriver.simple._

object DB {

  val db = Database.forURL(
    Configuration.database.core.jdbc.url,
    user = Configuration.database.core.username,
    password = Configuration.database.core.password,
    driver = Configuration.database.core.jdbc.driver
  )

}
