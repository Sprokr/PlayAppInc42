package requestDS

import java.io.File
import java.sql.Date
import java.util.UUID

import play.api.libs.functional.syntax._
import helper.CustomExceptions
import play.api.libs.Files
import play.api.libs.Files.TemporaryFile
import play.api.libs.json.{JsPath, JsResult, Json, Reads}
import play.api.mvc.{MultipartFormData, Request}

import scala.util.Try

/**
  * Created by sprokr on 21/08/17.
  */
case class FundingInfoRequestData(amount: Int, date: String, stage: String, investor: String)

case class AddProfileRequestData (name: String,
                                  description: String,
                                  logo: Option[String],
                                  markets: List[String],
                                  foundedOn: String,
                                  website: Option[String],
                                  linkedin: Option[String],
                                  facebook: Option[String],
                                  twitter: Option[String],
                                  phoneNumber: Option[Int],
                                  fundingDetails: List[FundingInfoRequestData]
                                  )




object AddProfileRequestData{

  implicit val fundingInfoRead = Json.reads[FundingInfoRequestData]
  implicit val profileInfoRead = Json.reads[AddProfileRequestData]

  def uploadLogo(request : Request[MultipartFormData[TemporaryFile]]): Option[String] = {
    request.body.file("file").map { file =>
      val filename = file.filename
      val filePath: String = s"/tmp/${UUID.randomUUID}_$filename"
      val uniqueFile = new File(filePath)

      file.ref.moveTo(uniqueFile, true)
      s"http://108.59.80.115"+filePath

    }
  }

  def getRequestPayload(request: Request[MultipartFormData[Files.TemporaryFile]]) : Try[AddProfileRequestData] = {
    Try{

      val companyData: String = (request.body.dataParts.get("companyInfo").getOrElse(List("")))(0)
      if(companyData == "" )
        throw CustomExceptions("Input fields are empty")

      val input = Json.parse(companyData)
      val restRequest: JsResult[AddProfileRequestData] = Json.fromJson(input)(AddProfileRequestData.profileInfoRead)
      val req: AddProfileRequestData = restRequest.get
      val logoPath = uploadLogo(request)


      val reqWithLogoPath = AddProfileRequestData(req.name, req.description, logoPath, req.markets, req.foundedOn, req.website, req.linkedin, req.facebook, req.twitter, req.phoneNumber, req.fundingDetails)



      reqWithLogoPath

    }
  }
}

//object AddProfileRequestDataParser{
//
//  implicit val fundingInfoRead: Reads[FundingInfoRequestData] = (
//    (JsPath \ "amount").read[Int].orElse(Reads.pure(0)) and
//      (JsPath \ "date").read[String].orElse(Reads.pure("")) and
//      (JsPath \ "stage").read[String].orElse(Reads.pure("")) and
//      (JsPath \ "investors").read[List[String]].orElse(Reads.pure(List()))
//    )(FundingInfoRequestData.apply _)
//
//  implicit val companyInfoRead: Reads[AddProfileRequestData] = (
//    (JsPath \ "name").read[String].orElse(Reads.pure("")) and
//      (JsPath \ "description").read[String].orElse(Reads.pure("")) and
//      (JsPath \ "logo").read[String].orElse(Reads.pure("")) and
//      (JsPath \ "markets").read[List[String]].orElse(Reads.pure(List())) and
//      (JsPath \ "foundedOn").read[String].orElse(Reads.pure("")) and
//      (JsPath \ "website").read[String].orElse(Reads.pure("")) and
//      (JsPath \ "fundingDetails").read[List[FundingInfoRequestData]].orElse(Reads.pure(List()))
//    )(AddProfileRequestData.apply _)
//
//  def readProfileInfo(jsonString: String) = {
//
//  }
//
//}