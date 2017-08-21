package models.databaseModels

import java.sql.Timestamp
import java.util.UUID

import com.google.inject.Inject
import play.api.db.NamedDatabase
import play.api.db.slick.DatabaseConfigProvider
import requestDS.{AddProfileRequestData}
import slick.driver.MySQLDriver
import models.database.SamplePlayAppDatabase.{fundingInfoTable, _}
import models.entities._
import play.api.libs.json.{JsString, JsValue}
import responseDS.{ErrorResponseData, GetProfileResponseData, ResultPartResponse}
import play.api.Play._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
/**
  * Created by sprokr on 21/08/17.
  */
object SamplePlayAppDbModels {
  @Inject()
  @NamedDatabase("Inc42") val dbConfig = DatabaseConfigProvider.get[MySQLDriver]

  import dbConfig.driver.api._



  def addProfile(profile: AddProfileRequestData): JsValue = {
    val profileId: String = UUID.randomUUID.toString
    val company = CompanyInfoEntity(1, profileId, profile.name, profile.description, profile.website match {
      case None => ""
      case x => x.get
    }, profile.logo match {
      case None => ""
      case x => x.get
    }, Timestamp.valueOf(profile.foundedOn+ " 00:00:00"))

    val companyId: Int = Await.result(dbConfig.db.run(
      companyInfoTable returning companyInfoTable.map(_.id) += company
    ), Duration.Inf)

    for (eachFund <- profile.fundingDetails) {

      val investor = InvestorsInfoEntity(1, eachFund.investor, eachFund.investor)

      val existOrNot: List[InvestorsInfoEntity] = Await.result(dbConfig.db.run(
        investorsInfoTable.filter(p => p.name === eachFund.investor).result
      ), Duration.Inf).toList

      val investorId = existOrNot.length match {
        case 0 => {
          Await.result(dbConfig.db.run(
            investorsInfoTable returning investorsInfoTable.map(_.id) += investor
          ), Duration.Inf)
        }
        case _ => {
          existOrNot(0).id
        }
      }

      val stage = StagesInfoEntity(1, eachFund.stage, eachFund.stage)

      val existOrNotStage: List[InvestorsInfoEntity] = Await.result(dbConfig.db.run(
        investorsInfoTable.filter(p => p.name === eachFund.stage).result
      ), Duration.Inf).toList

      val stageId = existOrNotStage.length match {
        case 0 => {
          Await.result(dbConfig.db.run(
            stagesInfoTable returning stagesInfoTable.map(_.id) += stage
          ), Duration.Inf)
        }
        case _ => {
          existOrNotStage(0).id
        }
      }

      val fund = FundingInfoEntity(1, eachFund.amount, companyId, investorId, stageId, Timestamp.valueOf(eachFund.date+ " 00:00:00"))
      val funding = Await.result(dbConfig.db.run(
        fundingInfoTable  += fund
      ), Duration.Inf)
    }

    val social = SocialInfoEntity(1, companyId,
      profile.twitter match {
        case None => ""
        case x => x.get
      },
      profile.linkedin match {
        case None => ""
        case x => x.get
      },
      profile.facebook match {
        case None => ""
        case x => x.get
      },
      profile.phoneNumber match {
        case None => 0
        case x => x.get
      }
    )

    val socialInfo = Await.result(
      dbConfig.db.run(
        socialInfoTable += social
      ), Duration.Inf
    )

    for (eachMarket <- profile.markets) {

      val market = MarketsInfoEntity(1, eachMarket, eachMarket)

      val existOrNotMarket: List[MarketsInfoEntity] = Await.result(dbConfig.db.run(
        marketsInfoTable.filter(p => p.name === eachMarket).result
      ), Duration.Inf).toList

      val marketId = existOrNotMarket.length match {
        case 0 => {
          Await.result(dbConfig.db.run(
            marketsInfoTable returning marketsInfoTable.map(_.id) += market
          ), Duration.Inf)
        }
        case _ => {
          existOrNotMarket(0).id
        }
      }

      val cmpMktMap = CompanyMarketsMapEntity(1,companyId, marketId)

      Await.result(
        dbConfig.db.run(
          companyMarketsMapTable += cmpMktMap
        ), Duration.Inf
      )

    }


    getProfileInfo(profileId)

  }


  def getProfileInfo(profileId: String): JsValue = {

   val company =  Await.result(dbConfig.db.run(
     companyInfoTable.filter(p => p.profileId === profileId).result
   ), Duration.Inf).toList

    val res = company.length match{
      case 0 => ErrorResponseData(ResultPartResponse(false, 200, None, Some("No company with this profile_id exists !!")),None)._asJson
      case _ => {

        val companyId = company(0).id

        val marketsInfo: List[MarketsInfoEntity] = (Await.result(
                    dbConfig.db.run(
                      (for((marketsInfo, cmpMktMap) <- (marketsInfoTable  join companyMarketsMapTable.filter(p => p.companyId === companyId) on (_.id === _.marketId))) yield marketsInfo).result
                      ), Duration.Inf)).toList

        val socialInfos: List[SocialInfoEntity] = (Await.result(
          dbConfig.db.run(
            socialInfoTable.filter(p => p.companyId === companyId).result
          ), Duration.Inf
        )).toList

        val socialInfo = socialInfos.length match{
          case 0 => SocialInfoEntity(1, companyId, "","","",0)
          case _ => socialInfos(0)
        }
        val fundingInfo: List[(StagesInfoEntity, FundingInfoEntity, InvestorsInfoEntity)] = (Await.result(
          dbConfig.db.run(
            (for(((fundingInfo, stagesInfo), investorsInfo )<- ( stagesInfoTable join fundingInfoTable.filter(p => p.companyId === companyId) on (_.id === _.stageId) join investorsInfoTable on (_._2.investorId === _.id))) yield (fundingInfo, stagesInfo, investorsInfo)).result
          ), Duration.Inf
        )).toList

        GetProfileResponseData.formResponse(company(0), marketsInfo, socialInfo, fundingInfo)._asJson
      }
    }
    res
  }


}
