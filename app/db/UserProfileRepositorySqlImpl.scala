package db

import com.github.tototoshi.slick.MySQLJodaSupport._
import dao.UserDataRepository
import models.UserProfile
import org.joda.time.DateTime
import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ProvenShape, Rep, TableQuery, Tag}

import java.awt.image.LookupTable
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserProfileRepositorySqlImpl @Inject()(db: Database)
  extends TableQuery(new UserProfileRepositoryTable(_))
    with UserDataRepository {

  def store(userProfile: UserProfile): Future[Boolean] = {
    db.run(this returning this.map(_.id) += userProfile) map (_ > 0)
  }

  def delete(email: String): Future[Boolean] = {
    db.run(this.filter(_.email === email).map(_.status).update(false)) map (_ > 0)
  }

  def getUserById(email: String): Future[Option[UserProfile]] = {
    db.run(this.filter(_.email === email).result.headOption)
  }

  def getAllUsers: Future[Seq[UserProfile]] = {
    db.run(this.filterNot(_.email === "").result)
  }

  def updateUserName(email: String, name: String): Future[Boolean] = {
    db.run(this.filter(_.email === email).map(_.firstName).update(name)) map ( _ > 0)
  }
}

class UserProfileRepositoryTable(tag: Tag) extends Table[UserProfile](tag, "user_profile") {

  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def firstName: Rep[String] = column[String]("first_name")

  def lastName: Rep[String] = column[String]("last_name")

  def email: Rep[String] = column[String]("email")

  def department: Rep[String] = column[String]("department")

  def created: Rep[DateTime] = column[DateTime]("created")

  def status: Rep[Boolean] = column[Boolean]("status")

  def * : ProvenShape[UserProfile] =
    (
      id,
      firstName,
      lastName,
      email,
      department,
      created,
      status
    ) <> (UserProfile.tupled, UserProfile.unapply)
}

