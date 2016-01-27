package controllers

import models.Sells
import play.api._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  def getSells = Action.async {
    Sells.findAll.map { sells =>
    Ok(Json.toJson(sells))}
  }
  def createSells = Action.async(parse.urlFormEncoded) { request =>
  Sells.create(request.body("cashid", "chcknumber", "SKU", "barcode", "name", "qty", "baseprice", "discountprice").head).map { sells =>
  Redirect(routes.Application.index())}
  }

}
