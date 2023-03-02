package controllers

import dao.UserDataRepository
import models.UserProfile

import javax.inject._
import play.api._
import play.api.data.Form
import play.api.data.Forms.{longNumber, mapping, nonEmptyText, optional}
import play.api.i18n.Lang.logger
import play.api.i18n.Messages
import play.api.mvc._
import views.html

import java.util.concurrent.TimeoutException
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               userProfile: UserDataRepository) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index("Welcome to SCALA PLAY"))
  }

  def create: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.index("Create User"))
  }

  def save: Action[AnyContent] = Action.async { implicit request =>
    userForm.bindFromRequest().fold(
      formWithError => {
        logger.info(s"Bad request while registration ${formWithError.errors}")
        Future.successful(BadRequest(views.html.index("Error")))
      },
      data => {
        val profile = UserProfile(0, data.firstName, data.lastName, data.email, data.department)
        userProfile.store(profile) map {
          case true => Ok
          case false => BadGateway
        }
      }
    )
  }

  def getAllUser: Action[AnyContent] = Action.async { implicit  request =>
    Future.successful(Ok(views.html.index(userProfile.getAllUsers.toString)))
  }

  def getUserByEmail(email: String): Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(Ok(views.html.index(userProfile.getUserById(email).toString)))
  }

  def deleteUser(email: String): Action[AnyContent] = Action.async {
    implicit request =>
      Future.successful(Ok(views.html.index(userProfile.delete(email).toString)))
  }

  def updateUserNameByEmail(email: String, name: String): Action[AnyContent] = Action.async {
    implicit request =>
      userProfile.updateUserName(email, name).map {
        case true => Ok
        case false => BadGateway
      }
  }


  val userForm: Form[User] = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "email" -> nonEmptyText,
      "department" -> nonEmptyText
      )(User.apply)(User.unapply))

  case class User(
                   firstName: String,
                   lastName: String,
                   email: String,
                   department: String
                 )
}
