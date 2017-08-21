package models.service

import models.databaseModels.SamplePlayAppDbModels
import play.api.libs.json.JsValue
import requestDS.{AddProfileRequestData}

/**
  * Created by sprokr on 21/08/17.
  */
object UserService {
  def getProfile(profileId: String): JsValue = SamplePlayAppDbModels.getProfileInfo(profileId)

  def addProfile(profile: AddProfileRequestData): JsValue = SamplePlayAppDbModels.addProfile(profile)
}
