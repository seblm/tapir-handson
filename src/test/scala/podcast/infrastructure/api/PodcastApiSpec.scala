package podcast.infrastructure.api

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers._

class PodcastApiSpec extends AnyFlatSpec {

  "tapir" should "be loaded in classpath" in {
    withClue("""Many of tapir functionalities come as builder methods in the main package, hence it’s easiest to work
               |with tapir if you import the main package entirely.
               |
               |To fix this test you should import "com.softwaremill.sttp.tapir" %% "tapir-core" % "0.17.9" into your
               |build.sbt.
               |
               |""".stripMargin) {

      "import sttp.tapir._" must compile

    }
  }

}
