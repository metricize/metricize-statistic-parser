package com.fustigatedcat.metricize.worker.model

import slick.driver.MySQLDriver.simple._

case class CustomerDatabaseMap(id : Long, fqdn : String, port : Int, dbName : String, username : String, password : String)

class CustomerDatabaseMaps(tag : Tag) extends Table[CustomerDatabaseMap](tag, "CustomerDatabaseMap") {
  def customerId = column[Long]("customer_id", O.PrimaryKey)
  def fqdn = column[String]("fqdn")
  def port = column[Int]("port")
  def dbName = column[String]("db_name")
  def username = column[String]("username")
  def password = column[String]("password")

  def * = (customerId, fqdn, port, dbName, username, password) <> (CustomerDatabaseMap.tupled, CustomerDatabaseMap.unapply)
}