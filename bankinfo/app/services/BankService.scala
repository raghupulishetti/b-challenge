package services

import javax.inject.Singleton

import scala.io.Source
import java.io.FileNotFoundException

import scala.io.BufferedSource
import java.io.IOException

import models.Bank
import javax.inject.Inject
import repositories.BankRepository

@Singleton
class BankService @Inject()(bankRepository: BankRepository) {
  def processCSV(): Unit = {
    val csvFile = "public/bankdetails.csv"
    var bufferedSource: BufferedSource = null;
    try {
      bufferedSource = Source.fromFile(csvFile)
      //delete all records from database initially
      bankRepository.deleteAll

      for (line <- bufferedSource.getLines) {
        val Array(name, bankIdentifier) = line.split(";").map(_.trim)

        if (!"name".equals(s"$name")) {
          var bank: Bank = new Bank(s"$name", s"$bankIdentifier".toLong);
          bankRepository.insert(bank)

        }
      }
    } catch {
      case e: FileNotFoundException => println(e)
      case e: IOException => println(e)
    } finally {
      if (bufferedSource != null) {
        try {
          bufferedSource.close;
        } catch {
          case e: IOException => println(e)
        }

      }
    }

  }

  def findById(bankIdentifier:Long):Option[Bank] = bankRepository.findById(bankIdentifier)
}
