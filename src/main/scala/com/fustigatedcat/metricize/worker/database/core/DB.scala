package com.fustigatedcat.metricize.worker.database.core

import com.fustigatedcat.metricize.worker.Configuration
import com.fustigatedcat.metricize.worker.model.CustomerDatabaseMaps

import scala.slick.driver.MySQLDriver
import scala.slick.driver.MySQLDriver.simple._

object DB {

  var customerDbs = Map[Long, MySQLDriver.backend.DatabaseDef]()

  val customerDatabaseMapTable = TableQuery[CustomerDatabaseMaps]

  val db = Database.forURL(
    Configuration.database.core.jdbc.url,
    user = Configuration.database.core.username,
    password = Configuration.database.core.password,
    driver = Configuration.database.core.jdbc.driver
  )

  def getCustomerDB(customerId : Long) = {
    def createDatabaseForCustomer(customerId : Long) = {
      db.withSession { implicit session =>
        customerDatabaseMapTable.filter(_.customerId === customerId).firstOption match {
          case Some(cdb) => Database.forURL(
            Configuration.database.customer.jdbc.template.replace("<fqdn>", cdb.fqdn).replace("<port>", cdb.port.toString).replace("<db_name>", cdb.dbName),
            user = cdb.username,
            password = cdb.password,
            driver = Configuration.database.customer.jdbc.driver
          )
        }
      }
    }

    this.synchronized {
      customerDbs.get(customerId) match {
        case Some(cdb) => cdb
        case _ => {
          val cdb = createDatabaseForCustomer(customerId)
          customerDbs = customerDbs + (customerId -> cdb)
          cdb
        }
      }
    }

  }

}
