package models

import play.api.libs.json.Json
import scalikejdbc.WrappedResultSet
import scalikejdbc._
import scalikejdbc.async._
import scalikejdbc.async.FutureImplicits._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by s on 27.01.2016.
  */
case class Sells(id: Long, cashid: Long, chcknumber: Long, SKU: String, barcode: String, name: String, qty: Long, baseprice: Long, discountprice: Long)
object Sells extends SQLSyntaxSupport[Sells] {
  implicit val jsonFormat = Json.format[Sells]
  override val columnNames = Seq("id", "cashid", "chcknumber", "SKU", "barcode", "name", "qty", "baseprice", "discountprice")
  lazy val s = Sells.syntax

  def db(s: SyntaxProvider[Sells])(rs: WrappedResultSet): Sells = db(s.resultName)(rs)

  def db(s: ResultName[Sells])(rs: WrappedResultSet): Sells = Sells(
  rs.long(s.id),
  rs.long(s.cashid),
  rs.long(s.chcknumber),
  rs.string(s.SKU),
  rs.string(s.barcode),
  rs.string(s.name),
  rs.long(s.qty),
  rs.long(s.baseprice),
  rs.long(s.discountprice)
  )

  def create(cashid: Long, chcknumber: Long, SKU: String, barcode: String, name: String, qty: Long, baseprice: Long, discountprice: Long)
            (implicit session: AsyncDBSession = AsyncDB.sharedSession): Future[Sells] = {
    val sql = withSQL(insert.into(Sells).namedValues(column.name -> name).returningId)
    //todo
    // some inspiration to this^ problem of tupled insert can be found here:
    //https://github.com/scalikejdbc/scalikejdbc-async/blob/develop/play-sample/app/models/Company.scala
    // so far it sucks
    // please advice ya@sbmart.ru
    sql.updateAndReturnGeneratedKey().map(id => Sells(id, cashid, chcknumber, SKU, barcode, name, qty, baseprice, discountprice))
  }

  def findAll(implicit session: AsyncDBSession = AsyncDB.sharedSession):
  Future[List[Sells]] = {
    withSQL(select.from[Sells](Sells as s)).map(Sells.db(s))
  }
}
