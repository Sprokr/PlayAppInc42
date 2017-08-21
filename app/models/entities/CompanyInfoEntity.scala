package models.entities

import java.sql.Timestamp

/**
  * Created by sprokr on 21/08/17.
  */
case class CompanyInfoEntity (id: Int, profileId: String, name: String, description: String, website: String, logo: String, founded_on: Timestamp)
