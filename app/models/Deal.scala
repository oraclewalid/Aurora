package models

import java.time.LocalDate

import infra.DoobieJdbc
import play.api.libs.json.Json
import doobie.postgres.imports._
import doobie.imports._

case class Deal(id :Long, name: String, startDate : LocalDate, lastDate :Option[LocalDate], creationDate : LocalDate, buyerOrSeller : String)


object Deal  extends DoobieJdbc{

  def save(deal : Deal) = {
    sql"""
          INSERT
          INTO deal("nomdeal", "startdate", "lastdate", "creationdate", "buyerorseller")
          VALUES (${deal.name}, ${deal.startDate}, ${deal.lastDate}, ${deal.creationDate}, ${deal.buyerOrSeller})
      """.update
      //.withUniqueGeneratedKeys[String]("id")
      .run.transact(xa).unsafePerformIO
  }

  def list = {
    sql"""
          SELECT "id","nomdeal", "startdate", "lastdate", "creationdate", "buyerorseller"
          FROM deal
      """.query[Deal]
      .list
      .transact(xa)
      .unsafePerformIO
  }

  implicit val creatorFormat = Json.format[Deal]
}
