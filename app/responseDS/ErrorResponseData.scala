package responseDS

import play.api.libs.json.Json

/**
  * Created by sprokr on 21/08/17.
  */

case class ErrorResponseData (result: ResultPartResponse, content : Option[String]){
  def _asJson = {
    implicit val resultPartWrite = Json.writes[ResultPartResponse]
    Json.toJson(this)(Json.writes[ErrorResponseData])
  }
}

object ErrorResponseData{
  def formErrorResponse(code: Int, msg : String): ErrorResponseData = {
    ErrorResponseData(ResultPartResponse(false, 200, Some(code), Some(msg)), None)
  }
}