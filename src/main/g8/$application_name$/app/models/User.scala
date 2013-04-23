package models

import securesocial.core.providers.Token
import securesocial.core._
import play.api.Play.current
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import com.mongodb.casbah.query.dsl._
import mongoContext._

case class User(id: UserId, firstName: String, lastName: String, fullName: String, email: Option[String],
                avatarUrl: Option[String], authMethod: AuthenticationMethod,
                oAuth1Info: Option[OAuth1Info] = None,
                oAuth2Info: Option[OAuth2Info] = None,
                passwordInfo: Option[PasswordInfo] = None) extends Identity

object User extends ModelCompanion[User, UserId] {
  def apply(i: Identity): User = {
    User(i.id, i.firstName, i.lastName, i.fullName,
      i.email, i.avatarUrl, i.authMethod, i.oAuth1Info,
      i.oAuth2Info, i.passwordInfo
    )
  }
  val dao = new SalatDAO[User, UserId](collection = mongoCollection("users")) {}

  def findOneBySocialId(socialId:UserId):Option[User] = dao.findOne(MongoDBObject("_id._id" -> socialId.id, "_id.providerId" -> socialId.providerId))
  def findOneByEmailAndProvider(email: String, providerId:String): Option[User] = dao.findOne(MongoDBObject("email" -> email, "authMethod.method" -> providerId))
}


object TokenDAO extends ModelCompanion[Token, String] {
  val dao = new SalatDAO[Token, String](collection = mongoCollection("tokens")) {}

  def findToken(uuid:String):Option[Token] = dao.findOne(MongoDBObject("uuid" -> uuid))
  def deleteExpiredTokens() {
    val now = new _root_.java.util.Date()
    dao.find("expirationTime" $lte now).foreach(
      TokenDAO.remove(_)
    )
  }
}

