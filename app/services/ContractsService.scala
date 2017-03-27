package services

import javax.inject.Singleton

import play.api.libs.json.Json

import scala.concurrent.Future

case class Contract(id: Int, fileName: String , category : String = "UNKNOWN")

object Contract{
  implicit val format = Json.format[Contract]
}

@Singleton
class ContractsService {
  @volatile private var contracts = Map(
      11 -> Contract(id = 11, fileName = "Contact-11"),
      12 -> Contract(id = 12, fileName = "Contact-12"),
      13 -> Contract(id = 13, fileName = "Contact-13"),
      14 -> Contract(id = 14, fileName = "Contact-14"),
      15 -> Contract(id = 15, fileName = "Contact-15")
  )

  def all: Future[List[Contract]] = Future.successful(contracts.values.toList.sortBy(_.id))

  def delete(id: Int):Future[Option[Contract]] = {
    val answer = contracts.get(id)
    contracts = contracts - id
    Future.successful(answer)
  }

  def create(fileName: String): Future[Contract] = Future.successful {
    val id = contracts.keys.max + 1
    contracts = contracts + (id -> Contract(id, fileName))
    Contract(id, fileName)
  }

  def update(id: Int, fileName: String): Future[Contract] = Future.successful {
    contracts = contracts + (id -> Contract(id, fileName))
    Contract(id, fileName)
  }
}
