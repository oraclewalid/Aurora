package controllers

import javax.inject.Inject

import models.{Bloc, Contract}
import play.api.libs.json._
import play.api.mvc.{Action, BodyParsers, Controller}
import services.ContractsService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ContractsController @Inject()(contractsService: ContractsService) extends Controller {

  private def toDataField[A](value: A)(implicit writes: Writes[A]): JsValue = {
    JsObject(Seq("data" -> Json.toJson(value)))
  }

  def upload() = Action(parse.temporaryFile) { request =>
//    val contract = request.map(tmpFile => contractsService.archive(Option(tmpFile.file))).body
//    Ok(Json.toJson(contract))
    Ok
  }

  def list =  Action {
    Ok(Json.toJson(contractsService.list))
  }

  def create = Action(BodyParsers.parse.json) { request =>
    val contractJson = request.body.validate[Contract]
    contractJson.fold(
      errors => {
        BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
      },
      contract => {
        Contract.save(contract)
        Ok(Json.obj("status" ->"OK", "message" -> (s"${contract.nomContrat} was created") ))
      }
    )
  }

  def get(contractId: Long) = Action {
    val contract = Contract.get(contractId)
    contract.fold(NotFound("id not found")) {
      val blocs = Bloc.list(contractId)
      contract => Ok(Json.obj("contract" -> Json.toJson(contract), "blocs" -> Json.toJson(blocs)))
    }
  }

}
