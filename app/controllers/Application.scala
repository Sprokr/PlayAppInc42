package controllers

import models.service.UserService
import play.api.libs.json.JsValue
import play.api.mvc.{Action, Controller}
import requestDS.AddProfileRequestData
import responseDS.ErrorResponseData

import scala.util.{Failure, Success, Try}

/**
  * Created by sprokr on 21/08/17.
  */
class Application extends Controller{

  def index = Action{
    Ok("Welcome to Inc42 Play App !!")
  }

  def getProfile(profileId: String) = Action{
    val result = Try{
      UserService.getProfile(profileId)

    }
    val res = result match{
      case Failure(f) => ErrorResponseData.formErrorResponse(500,f.getMessage)._asJson
      case Success(e) => e
    }
    Ok(res)
  }

  def addProfile = Action(parse.multipartFormData){
    implicit request => val reqPayload: Try[AddProfileRequestData] = AddProfileRequestData.getRequestPayload(request)

      val result: JsValue = reqPayload match{
        case Failure(f) =>ErrorResponseData.formErrorResponse(500,f.getMessage)._asJson
        case Success(req: AddProfileRequestData) => {
          UserService.addProfile(req)
        }
      }

      Ok(result)
  }
}
