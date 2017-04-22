package services

import java.io.File
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
      11 -> Contract(id = "11", fileName = "Contact-11", oid = 12),
      12 -> Contract(id = "12", fileName = "Contact-12", oid = 12),
      13 -> Contract(id = "13", fileName = "Contact-13", oid = 12),
      14 -> Contract(id = "14", fileName = "Contact-14", oid = 12),
      15 -> Contract(id = "15", fileName = "Contact-15", oid = 12)
  )

  def all: Future[List[Contract]] = Future.successful(contracts.values.toList.sortBy(_.id))

  def delete(id: Int):Future[Option[Contract]] = {
    val answer = contracts.get(id)
    contracts = contracts - id
    Future.successful(answer)
  }

  def create(fileName: String): Future[Contract] = Future.successful {
    val id = contracts.keys.max + 1
    contracts = contracts + (id -> Contract(id.toString, fileName, oid = 102))
    Contract(id.toString, fileName, oid = 112)
  }

  def update(id: Int, fileName: String): Future[Contract] = Future.successful {
    contracts = contracts + (id -> Contract(id.toString, fileName, oid = 102))
    Contract(id.toString, fileName, oid = 112)
  }
}
