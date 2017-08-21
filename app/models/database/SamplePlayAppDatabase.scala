package models.database

import slick.lifted.TableQuery
import slick.driver.MySQLDriver.api._
import java.sql.Timestamp

import models.entities._

/**
  * Created by sprokr on 21/08/17.
  */
object SamplePlayAppDatabase {
  val companyInfoTable = TableQuery[CompanyInfo]
  val marketsInfoTable = TableQuery[MarketsInfo]
  val socialInfoTable = TableQuery[SocialInfo]
  val fundingInfoTable = TableQuery[FundingInfo]
  val stagesInfoTable = TableQuery[StagesInfo]
  val companyMarketsMapTable = TableQuery[CompanyMarketsMap]
  val investorsInfoTable = TableQuery[InvestorsInfo]
  }

class CompanyInfo(tag: Tag) extends Table[CompanyInfoEntity](tag, "company_info") {
  def id       = column[Int]("id", O.PrimaryKey, O.AutoInc, O.SqlType("INT(11)"))
  def profileId      = column[String]("profile_id", O.SqlType("VARCHAR(50)"))
  def description = column[String]("description", O.SqlType("VARCHAR(250)"))
  def name      = column[String]("name", O.SqlType("VARCHAR(100)"))
  def logo = column[String]("logo", O.SqlType("VARCHAR(250)"))
  def website = column[String]("website", O.SqlType("VARCHAR(250)"))
  def foundedOn = column[Timestamp]("founded_on", O.SqlType("TIMESTAMP"))
  def * =
    (id,
      profileId,
      name,
      description,
      website,
      logo,
      foundedOn) <> (CompanyInfoEntity.tupled, CompanyInfoEntity.unapply)
}

class MarketsInfo(tag: Tag) extends Table[MarketsInfoEntity](tag, "markets_info") {
  def id       = column[Int]("id", O.PrimaryKey, O.AutoInc, O.SqlType("INT(11)"))
  def name      = column[String]("name", O.SqlType("VARCHAR(50)"))
  def description = column[String]("description", O.SqlType("VARCHAR(50)"))
  def * =
    (id,
      name,
      description) <> (MarketsInfoEntity.tupled, MarketsInfoEntity.unapply)
}


class SocialInfo(tag: Tag) extends Table[SocialInfoEntity](tag, "social_info") {
  def id       = column[Int]("id", O.PrimaryKey, O.AutoInc, O.SqlType("INT(11)"))
  def companyId      = column[Int]("company_id", O.SqlType("INT(11)"))
  def twitter = column[String]("twitter", O.SqlType("VARCHAR(150)"))
  def linkedin = column[String]("linkedin", O.SqlType("VARCHAR(150)"))
  def facebook = column[String]("facebook", O.SqlType("VARCHAR(150)"))
  def phoneNumber   = column[Int]("phone_number", O.SqlType("INT(11)"))
  def * =
    (id,
      companyId,
      twitter,
      linkedin,
      facebook,
      phoneNumber) <> (SocialInfoEntity.tupled, SocialInfoEntity.unapply)
}

class FundingInfo(tag: Tag) extends Table[FundingInfoEntity](tag, "funding_info") {
  def id       = column[Int]("id", O.PrimaryKey, O.AutoInc, O.SqlType("INT(11)"))
  def companyId      = column[Int]("company_id", O.SqlType("INT(11)"))
  def amount      = column[Int]("amount", O.SqlType("INT(11)"))
  def investorId      = column[Int]("investor_id", O.SqlType("INT(11)"))
  def stageId      = column[Int]("stage_id", O.SqlType("INT(11)"))
  def date = column[Timestamp]("date", O.SqlType("TIMESTAMP"))
  def * =
    (id,
      amount,
      companyId,
      investorId,
      stageId,
      date) <> (FundingInfoEntity.tupled, FundingInfoEntity.unapply)
}



class StagesInfo(tag: Tag) extends Table[StagesInfoEntity](tag, "stages_info") {
  def id       = column[Int]("id", O.PrimaryKey, O.AutoInc, O.SqlType("INT(11)"))
  def name      = column[String]("name", O.SqlType("VARCHAR(50)"))
  def description = column[String]("description", O.SqlType("VARCHAR(50)"))
  def * =
    (id,
      name,
      description) <> (StagesInfoEntity.tupled, StagesInfoEntity.unapply)
}

class CompanyMarketsMap(tag: Tag) extends Table[CompanyMarketsMapEntity](tag, "company_market_map") {
  def id       = column[Int]("id", O.PrimaryKey, O.AutoInc, O.SqlType("INT(11)"))
  def companyId      = column[Int]("company_id", O.SqlType("INT(11)"))
  def marketId      = column[Int]("market_id", O.SqlType("INT(11)"))
  def * =
    (id,
      companyId,
      marketId) <> (CompanyMarketsMapEntity.tupled, CompanyMarketsMapEntity.unapply)
}

class InvestorsInfo(tag: Tag) extends Table[InvestorsInfoEntity](tag, "investors_info") {
  def id       = column[Int]("id", O.PrimaryKey, O.AutoInc, O.SqlType("INT(11)"))
  def name      = column[String]("name", O.SqlType("VARCHAR(50)"))
  def description = column[String]("description", O.SqlType("VARCHAR(50)"))
  def * =
    (id,
      name,
      description) <> (InvestorsInfoEntity.tupled, InvestorsInfoEntity.unapply)
}

