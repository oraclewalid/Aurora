
package controllers

import java.time.LocalDate
import javax.inject.Inject

import models.{Bloc, Contract, Deal}
import play.api.libs.json._
import play.api.mvc.{Action, BodyParsers, Controller}
import services.ContractsService

import scala.concurrent.Future

class BlocController @Inject()(contractsService: ContractsService) extends Controller {


  def list =  Action {
    Ok(Json.toJson(Bloc.list(1)))
  }

  def create = Action(BodyParsers.parse.json) { request =>
    val blocJson = request.body.validate[Bloc]
    blocJson.fold(
      errors => {
        BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
      },
      bloc => {
        Bloc.save(bloc)
        Ok(Json.obj("status" ->"OK", "message" -> (s"${bloc.id} was created") ))
      }
    )
  }

  def update = Action(BodyParsers.parse.json) { request =>
    val blocJson = request.body.validate[Bloc]
    blocJson.fold(
      errors => {
        BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
      },
      bloc => {
        Bloc.update(bloc)
        Ok(Json.obj("status" ->"OK", "message" -> (s"${bloc.id} was updated") ))
      }
    )
  }

  def get(contractId: Long) = Action {
    Ok(Json.toJson(Bloc.list(contractId)))
  }

}

