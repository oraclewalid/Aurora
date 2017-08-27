package services

import java.io.File
import java.sql.Timestamp
import javax.inject.Singleton

import models.Contract
import play.api.libs.json.Json

import scala.concurrent.Future

@Singleton
class ContractsService {

//  def archive(maybeFile: Option[File]) = {
//    for{
//      file <- maybeFile
//      oid <- Contract.archive(file)
//    } yield Contract.create(Contract.from(file,oid))
//  }

  def list() = {
    Contract.list
  }

  def create(contract: Contract) = Contract.save(contract)

  def update(id: Int, fileName: String) = ???
}
