package controllers

import java.io.File
import javax.inject.Inject

import play.api.Logger
import play.api.libs.json.{JsObject, JsValue, Json, Writes}
import play.api.mvc.{Action, Controller}
import services.ContractsService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ContractsController @Inject()(contractsService: ContractsService) extends Controller {

  private def toDataField[A](value: A)(implicit writes: Writes[A]): JsValue = {
    JsObject(Seq("data" -> Json.toJson(value)))
  }

  def delete(id: Int) = Action.async {
    contractsService.delete(id).map { _ => Ok("") }
  }

  def create = Action.async(parse.json) { implicit request =>
    (request.body \ "name").asOpt[String]
      .map { name =>
        contractsService
          .create(name)
          .map(s => Ok(toDataField(s)))
      }.getOrElse(Future.successful(BadRequest("expected 'name'")))
  }

  def update(id: Int) = Action.async(parse.json) { implicit request =>
    (request.body \ "name").asOpt[String]
      .map { name =>
        contractsService.update(id, name)
          .map(s => Ok(toDataField(s)))
      }.getOrElse(Future.successful(BadRequest("expected 'name'")))
  }

  def upload() = Action(parse.temporaryFile) { request =>
    val contract = request.map(tmpFile => contractsService.archive(Option(tmpFile.file))).body
    Ok(Json.toJson(contract))
  }

  def list =  Action {
    Ok(Json.toJson(contractsService.list))
  }

}
