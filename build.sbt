name := """slick_play_demo_crud"""
organization := "com.knoldus"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies += guice
libraryDependencies ++= Seq("org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
                            "com.typesafe.play" %% "play-slick" % "5.0.0",
                            "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
                            "com.github.tototoshi" %% "slick-joda-mapper" % "2.4.2",
                            )

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
