val scala3Version = "3.7.4"

lazy val root = project
  .in(file("."))
  .settings(
    name := "tapir-handson",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.5.21" % Runtime,
    libraryDependencies += "com.github.pjfanning" %% "pekko-http-play-json" % "3.7.0" % Test,
    libraryDependencies += "com.softwaremill.sttp.apispec" %% "openapi-circe-yaml" % "0.11.10",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.12.4",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-json-play" % "1.12.4",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % "1.12.4",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-pekko-http-server" % "1.12.4",
    libraryDependencies += "org.apache.pekko" %% "pekko-actor-typed" % "1.3.0",
    libraryDependencies += "org.apache.pekko" %% "pekko-http" % "1.3.0",
    libraryDependencies += "org.apache.pekko" %% "pekko-http-testkit" % "1.3.0" % Test,
    libraryDependencies += "org.apache.pekko" %% "pekko-stream" % "1.3.0",
    libraryDependencies += "org.apache.pekko" %% "pekko-testkit" % "1.3.0" % Test,
    libraryDependencies += "org.scalameta" %% "munit" % "1.2.1" % Test,
    libraryDependencies += "org.slf4j" % "slf4j-api" % "2.0.17"
  )
