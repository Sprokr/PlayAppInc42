
name := "SamplePlayApp"

version := "1.0"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala, DebianPlugin)

libraryDependencies ++= Seq(
  jdbc,
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  //  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1",
  "mysql" % "mysql-connector-java" % "5.1.38"
)
routesGenerator := InjectedRoutesGenerator

maintainer in Linux := "SpRokr <sumit@ionosnetworks.com>"

packageSummary in Linux := "Api code for api.ionosnetworks.com"

packageDescription := "Play framework application backing up api.ionosnetworks.com"