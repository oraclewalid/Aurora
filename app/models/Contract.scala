package models

import java.io.File
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.{LocalDate, LocalDateTime}

import com.typesafe.config.ConfigFactory
import doobie.postgres.imports._
import infra.DoobieJdbc
import models.Contract.ContratId
import play.api.libs.json._

import scala.runtime.Nothing$
import scala.util.Try

case class Contract(id :Long, dealId :Long, nomContrat :String, nomClient :String , typeContrat : String, startDate : LocalDate, lastDate : LocalDate, creationDate : LocalDate, buyerOrSeller : String)



object Contract extends DoobieJdbc{

  type ContratId = String

  import cats._

  import cats.data._
  import cats.implicits._
  import doobie.imports._
  import xa.yolo._

  def from(maybeFile: File, largeFile: Long) : Contract = {
    null
  }

  def archive(file : File): Option[Long] = {
    Try{

      val prog: LargeObjectManagerIO[Long] =
      for {
        oid <- PHLOM.createLOFromFile(1024,file)
      } yield oid
      val task = PHC.pgGetLargeObjectAPI(prog).transact(xa)

      task.unsafePerformIO
    }.toOption
  }

  def save(contract: Contract) = {

    sql"""
          INSERT
          INTO  contract("dealid","nomcontrat", "nomclient","typecontrat","startdate", "lastdate", "creationdate", "buyerorseller")
          VALUES (${contract.dealId}, ${contract.nomContrat}, ${contract.nomClient}, ${contract.typeContrat},
          ${contract.startDate}, ${contract.lastDate}, ${contract.creationDate}, ${contract.buyerOrSeller})
      """.update
      .run
      .transact(xa)
      .unsafePerformIO
  }

  def list : Seq[Contract]= {
    sql"""
          SELECT *
          FROM contract
      """.query[Contract]
      .list
      .transact(xa)
      .unsafePerformIO
  }

  def get(contractId :Long) : Option[Contract]= {
    sql"""
          SELECT *
          FROM contract where id = ${contractId}
      """.query[Contract]
      .option
      .transact(xa)
      .unsafePerformIO
  }

  implicit val format = Json.format[Contract]

}
