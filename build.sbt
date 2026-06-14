name := """play_sales_system"""
organization := "com.jtracos"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).dependsOn(common)
lazy val common = (project in file("common")).settings(commonSetting)

val commonSetting = Seq(
  organization := "com.common",
  version := "1.0-SNAPSHOT" ,
  scalaVersion := "2.13.18"
)

scalaVersion := "2.13.18"

libraryDependencies += guice
libraryDependencies ++= Seq(
    "org.playframework" %% "play-slick" % "6.2.0",
    "mysql" % "mysql-connector-java" % "8.0.33",
    "joda-time" % "joda-time" % "2.0",
    "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.jtracos.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.jtracos.binders._"
