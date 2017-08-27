package models

import java.time.LocalDate

import infra.DoobieJdbc
import play.api.libs.json.Json
import doobie.postgres.imports._
import doobie.imports._

case class Bloc(id :Long, contractId :Long, categorie: Option[String], clause :String, important :Boolean,
                validSuperviseur : Boolean,validCompliance :Boolean, kpiNbModif :Int)


object Bloc  extends DoobieJdbc{

  def save(bloc: Bloc) = {

    sql"""
          INSERT
          INTO bloc("contractid","categorie", "clause","important","validsuperviseur", "validcompliance", "kpinbmodif")
          VALUES (${bloc.contractId}, ${bloc.categorie}, ${bloc.clause}, ${bloc.important}, ${bloc.validSuperviseur}, ${bloc.validCompliance}, ${bloc.kpiNbModif})
      """.update
      .run.transact(xa).unsafePerformIO
  }

  def update(bloc: Bloc) = {

    sql"""
          update bloc set
          "categorie" = ${bloc.categorie},
          "clause" = ${bloc.clause},
          "important" =  ${bloc.important},
          "validsuperviseur" =  ${bloc.validSuperviseur},
          "validcompliance" = ${bloc.validCompliance},
          "kpinbmodif" = ${bloc.kpiNbModif}
          where id = ${bloc.id}
      """.update
      .run.transact(xa).unsafePerformIO
  }

  def list(contractId : Long) = {
    sql"""
          SELECT "id","contractid","categorie", "clause","important","validsuperviseur", "validcompliance", "kpinbmodif"
          FROM bloc where contractid = ${contractId}
      """.query[Bloc]
      .list
      .transact(xa)
      .unsafePerformIO
  }

  implicit val format = Json.format[Bloc]
}
