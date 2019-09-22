package repositories

import javax.inject.{Inject, Singleton}
import play.api.db._
import anorm._
import anorm.SqlParser._
import models.Bank

@Singleton
class BankRepository @Inject() (db: Database) {
  private val bankParser = str("name") ~ long("bank_identifier") map {
    case name ~ bankIdentifier => Bank(name, bankIdentifier)
  }

  def findById(bankIdentifier: Long) :Option[Bank] = db.withConnection { implicit c =>
    SQL("SELECT * FROM bank WHERE bank_identifier = {bankIdentifier}")
      .on("bankIdentifier" -> bankIdentifier)
      .as(bankParser.singleOpt)
  }

  def insert(bank: Bank): Unit = {
    db.withConnection { implicit connection =>

      SQL("Insert into bank(name, bank_identifier) values ({name}, {bankIdentifier})")
        .on('name -> bank.name, 'bankIdentifier -> bank.bankIdentifier)
        .execute()
    }
  }

  def deleteAll(): Unit = {
    db.withConnection { implicit connection =>
      SQL"delete from bank".executeUpdate()
    }
  }
}