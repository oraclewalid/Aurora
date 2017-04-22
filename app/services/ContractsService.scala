package services

import java.io.File
import java.sql.Timestamp
import javax.inject.Singleton

import models.Contract
import play.api.libs.json.Json

import scala.concurrent.Future

@Singleton
class ContractsService {

  def archive(maybeFile: Option[File]) = {
    for{
      file <- maybeFile
      oid <- Contract.archive(file)
    } yield Contract.create(Contract.from(file,oid))
  }

  def list() = {
    Contract.list
  }

  @volatile private var contracts = Map(
      15 -> Contract(id = "15", fileName = "Contact-15", oid = 12, creationDate = new Timestamp(System.currentTimeMillis()))
  )

  def delete(id: Int):Future[Option[Contract]] = {
    val answer = contracts.get(id)
    contracts = contracts - id
    Future.successful(answer)
  }

  def create(fileName: String): Future[Contract] = Future.successful {
    contracts.get(15).get
  }

  def update(id: Int, fileName: String): Future[Contract] = Future.successful {
    contracts.get(15).get
  }
}
