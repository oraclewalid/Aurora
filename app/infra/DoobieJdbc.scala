package infra

import cats._
import cats.data._
import cats.implicits._
import com.typesafe.config.ConfigFactory
import doobie.imports._

trait DoobieJdbc {

  val driver = ConfigFactory.load().getString("db.default.driver")
  val url = ConfigFactory.load().getString("db.default.url")
  val user = ConfigFactory.load().getString("db.default.username")
  val password = ConfigFactory.load().getString("db.default.password")

  val xa = DriverManagerTransactor[IOLite](
    driver, url, user, password
  )
}
