package controllers

import java.time.LocalDate
import javax.inject.Inject

import models.Deal
import play.api.libs.json._
import play.api.mvc.{Action, BodyParsers, Controller}
import services.ContractsService

import scala.concurrent.Future

class DealController @Inject()(contractsService: ContractsService) extends Controller {

  def get =  Action {
    Ok(Json.toJson(Deal(12, "first deal", LocalDate.now(), Option(LocalDate.now()),LocalDate.now(), "buyer")))
  }

  def list =  Action {
    Ok(Json.toJson(Deal.list))
  }

  def create = Action(BodyParsers.parse.json) { request =>
    val dealJson = request.body.validate[Deal]
    dealJson.fold(
      errors => {
        BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
      },
      deal => {
        Deal.save(deal)
        Ok(Json.obj("status" ->"OK", "message" -> (s"${deal.name} was created") ))
      }
    )
  }

}
