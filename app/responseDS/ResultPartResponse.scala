package responseDS

import play.api.libs.json.Json

/**
  * Created by sprokr on 21/08/17.
  */
case class ResultPartResponse(success: Boolean, httpCode: Int, errorCode: Option[Int], errorMsg: Option[String]){
  def _asJson = {
    Json.toJson(this)(Json.writes[ResultPartResponse])
  }
}