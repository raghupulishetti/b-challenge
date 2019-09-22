package models

import play.api.libs.json.Json

case class Bank(name: String, bankIdentifier: Long)

object Bank {
  implicit val writer = Json.writes[Bank]
}
