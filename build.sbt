val scala3Version = "3.7.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "tapir-handson",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.5.20" % Runtime,
    libraryDependencies += "com.softwaremill.sttp.apispec" %% "openapi-circe-yaml" % "0.11.10",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.12.2",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-json-play" % "1.12.2",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % "1.12.2",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-pekko-http-server" % "1.12.2",
    libraryDependencies += "org.apache.pekko" %% "pekko-actor-typed" % "1.2.1",
    libraryDependencies += "org.apache.pekko" %% "pekko-http" % "1.3.0",
    libraryDependencies += "org.apache.pekko" %% "pekko-stream" % "1.2.1",
    libraryDependencies += "org.scalameta" %% "munit" % "1.2.1" % Test,
    libraryDependencies += "org.slf4j" % "slf4j-api" % "2.0.17",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % "1.12.3",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-redoc-bundle" % "1.12.3"


)
