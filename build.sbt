import Dependencies._

ThisBuild / scalaVersion := "2.13.4"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "handson.tapir"
ThisBuild / organizationName := "tapir-handson"

lazy val root = (project in file("."))
  .settings(
    name := "tapir-handson",
    libraryDependencies += `akka-actor-typed`,
    libraryDependencies += `akka-http`,
    libraryDependencies += `akka-stream`,
    libraryDependencies += `tapir-akka-http-server`,
    libraryDependencies += `tapir-core`,
    libraryDependencies += `tapir-json-play`,
    libraryDependencies += `tapir-openapi-circe-yaml`,
    libraryDependencies += `tapir-openapi-docs`,
    libraryDependencies += `tapir-redoc-akka-http`,
    libraryDependencies += `tapir-swagger-ui-akka-http`,
    libraryDependencies += scalatest % Test
  )
