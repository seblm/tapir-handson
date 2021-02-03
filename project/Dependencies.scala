import sbt._

object Dependencies {
  lazy val `akka-actor-typed` = "com.typesafe.akka" %% "akka-actor-typed" % "2.6.12"
  lazy val `akka-http` = "com.typesafe.akka" %% "akka-http" % "10.2.3"
  lazy val `akka-stream` = "com.typesafe.akka" %% "akka-stream" % "2.6.12"
  lazy val `tapir-akka-http-server` = "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server" % "0.17.9"
  lazy val `tapir-core` = "com.softwaremill.sttp.tapir" %% "tapir-core" % "0.17.9"
  lazy val `tapir-json-play` = "com.softwaremill.sttp.tapir" %% "tapir-json-play" % "0.17.9"
  lazy val `tapir-openapi-circe-yaml` = "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % "0.17.9"
  lazy val `tapir-openapi-docs` = "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % "0.17.9"
  lazy val `tapir-redoc-akka-http` = "com.softwaremill.sttp.tapir" %% "tapir-redoc-akka-http" % "0.17.9"
  lazy val `tapir-swagger-ui-akka-http` = "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-akka-http" % "0.17.9"
  lazy val scalatest = "org.scalatest" %% "scalatest" % "3.2.3"
}
