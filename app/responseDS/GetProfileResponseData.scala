package responseDS

import java.sql.Timestamp

import models.database.{CompanyInfo, MarketsInfo}
import models.entities._
import play.api.libs.json.{Json, Writes}

/**
  * Created by sprokr on 21/08/17.
  */
case class FundingInfo(amount: Int, date: String, stage: String, investor: String)

case class ProfileInfo (profileId: String,
                        name: String,
                        description: String,
                        logo: Option[String],
                        markets: List[String],
                        foundedOn: String,
                        website: Option[String],
                        linkedin: Option[String],
                        facebook: Option[String],
                        twitter: Option[String],
                        phoneNumber: Option[Int],
                        fundingDetails: List[FundingInfo]
                       )

case class GetProfileResponseData(result: ResultPartResponse, content: Option[ProfileInfo])
{
  def _asJson = {
    implicit val fundingDataWrite = Json.writes[FundingInfo]
    implicit val companyDataWrite = Json.writes[ProfileInfo]
    implicit val resultPartWrite = Json.writes[ResultPartResponse]
    Json.toJson(this)(Json.writes[GetProfileResponseData])
  }
}


object GetProfileResponseData{
  def formResponse(company: CompanyInfoEntity, marketsInfo: List[MarketsInfoEntity], socialInfo: SocialInfoEntity, fundingInfo: List[(StagesInfoEntity, FundingInfoEntity, InvestorsInfoEntity)]): GetProfileResponseData = {

    val markets: List[String] = (for(eachMkt <- marketsInfo) yield eachMkt.name).toList

    val funds: List[FundingInfo] = (for(eachFI <- fundingInfo) yield FundingInfo(eachFI._2.amount, eachFI._2.date.toString, eachFI._1.name, eachFI._3.name)).toList



    val profile: ProfileInfo = ProfileInfo(
      company.profileId,
      company.name,
      company.description,
      company.logo match{
        case "" => None
        case x => Some(x)
      },
      markets,
      company.founded_on.toString,
      company.website match{
        case "" => None
        case x => Some(x)
      },
      socialInfo.linkedin match{
        case "" => None
        case x => Some(x)
      },
      socialInfo.facebook match{
        case "" => None
        case x => Some(x)
      },
      socialInfo.twitter match{
        case "" => None
        case x => Some(x)
      },
      socialInfo.phoneNumber match{
        case 0 => None
        case x => Some(x)
      },
      funds
    )

    GetProfileResponseData(ResultPartResponse(true, 200, None, None), Some(profile))
  }
}