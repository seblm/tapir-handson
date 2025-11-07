package podcast.infrastructure.api

import munit.FunSuite

class PodcastApiSuite extends FunSuite:

  test("tapir should be loaded in classpath"):
    assertEquals(
      compileErrors("import sttp.tapir.*"),
      "",
      """Many of tapir functionalities come as builder methods in the main package, hence it’s easiest to work with
        |tapir if you import the main package entirely.
        |
        |To fix this test you should import "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.12.3" into your
        |build.sbt.
        |
        |""".stripMargin
    )
