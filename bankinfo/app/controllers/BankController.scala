package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json.Json
import repositories._
import services.BankService

@Singleton
class BankController @Inject() (cc: ControllerComponents, bankService: BankService) extends AbstractController(cc) {

  def processCSV() = Action { implicit request =>
    // Process CSV and insert data to DB
    bankService.processCSV();
    Ok(views.html.index("CSV processed and saved to database successfully..."))
  }

  def getById(bankIdentifier: Long) = Action { implicit request =>

    // Fetch the specific record from DB
    bankService.findById(bankIdentifier) match {
      case Some(bank) => {
        println("Result for the Id 10040000: "+Json.toJson(bank))
        Ok(views.html.bank(Json.toJson(bank).toString()))

      }
      case None => NotFound
    }

  }
}
