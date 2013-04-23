package services

import securesocial.core.{Identity, UserId, UserServicePlugin}
import play.api.Application
import models.{User,TokenDAO}
import securesocial.core.providers.Token
import play.api.Logger

class UserService(application: Application) extends UserServicePlugin(application) {
  /**
   * Finds a user that matches the specified id
   *
   * @param id the user id
   * @return an optional user
   */
  def find(id: UserId):Option[Identity] = {
    User.findOneBySocialId(id)
  }

  /**
   * Finds a user by email and provider id.
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation.
   *
   * @param email - the user email
   * @param providerId - the provider id
   * @return
   */
  def findByEmailAndProvider(email: String, providerId: String):Option[Identity] =
  {
  	User.findOneByEmailAndProvider(email, providerId)

  }

  /**
   * Saves the user.  This method gets called when a user logs in.
   * This is your chance to save the user information in your backing store.
   * @param user
   */
  def save(user: Identity) = {
    val u = User(user)
    User.save(u)
    u
  }

  /**
   * Saves a token.  This is needed for users that
   * are creating an account in the system instead of using one in a 3rd party system.
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   * @param token The token to save
   * @return A string with a uuid that will be embedded in the welcome email.
   */
  def save(token: Token) = {
    TokenDAO.save(token)
  }


  /**
   * Finds a token
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   * @param token the token id
   * @return
   */
  def findToken(token: String): Option[Token] = {
    TokenDAO.findToken(token)
  }

  /**
   * Deletes a token
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   * @param uuid the token id
   */
  def deleteToken(uuid: String) {
    val token = TokenDAO.findToken(uuid)
    if(token.isDefined){
      TokenDAO.remove(token.get)
    }
  }

  /**
   * Deletes all expired tokens
   *
   * Note: If you do not plan to use the UsernamePassword provider just provide en empty
   * implementation
   *
   */
  def deleteExpiredTokens() {
    TokenDAO.deleteExpiredTokens()
  }
}