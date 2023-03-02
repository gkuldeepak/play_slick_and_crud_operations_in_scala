package dao

import models.UserProfile

import scala.concurrent.Future

trait UserDataRepository {

  def store(userProfile: UserProfile): Future[Boolean]

  def delete(email: String): Future[Boolean]

  def getUserById(email: String): Future[Option[UserProfile]]

  def getAllUsers: Future[Seq[UserProfile]]

  def updateUserName(email: String, name: String): Future[Boolean]

}
