package models

import java.io.File
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime

import com.typesafe.config.ConfigFactory
import doobie.postgres.imports._
import infra.DoobieJdbc
import models.Contract.ContratId
import play.api.libs.json._

import scala.util.Try

case class Contract(id : ContratId, fileName: String , category : String = "UNKNOWN", creationDate : Timestamp, oid : Long)



object Contract extends DoobieJdbc{

  type ContratId = String

  import cats._

  import cats.data._
  import cats.implicits._
  import doobie.imports._
  import xa.yolo._

  def from(maybeFile: File, largeFile: Long) : Contract = {
     Contract(fileName= maybeFile.getName, oid = largeFile, id = "42", creationDate = new Timestamp(System.currentTimeMillis()))
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

  def create(contract: Contract) = {

    sql"""
          INSERT
          INTO contract (fileName, category,creationDate , oid)
          VALUES (${contract.fileName}, ${contract.category}, now(), ${contract.oid})
      """.update
      //.withUniqueGeneratedKeys[String]("id")
      .run.transact(xa).unsafePerformIO
  }

  def list : Seq[Contract]= {
    sql"""
          SELECT id, filename, category,creationDate, oid
          FROM contract
      """.query[Contract]
      .list
      .transact(xa)
      .unsafePerformIO
  }

  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

  implicit val format = Json.format[Contract]

}
