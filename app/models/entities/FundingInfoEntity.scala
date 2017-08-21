package models.entities

import java.sql.Timestamp

/**
  * Created by sprokr on 21/08/17.
  */
case class FundingInfoEntity (id: Int, amount: Int, companyId: Int, investorId: Int, stageId: Int, date: Timestamp)
