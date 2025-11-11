package podcast.infrastructure.api

import com.github.pjfanning.pekkohttpplayjson.PlayJsonSupport
import munit.FunSuite
import org.apache.pekko.http.scaladsl.testkit.{MUnitRouteTest, ScalatestRouteTest}

class PodcastServerSuite extends FunSuite with MUnitRouteTest with PlayJsonSupport:

  test("PodcastServer should expose endpoint with pekko-http"):
    Get("/api/v1/categories") ~> PodcastServer.route ~> check:
      val categories: Map[String, Int] = responseAs[Map[String, Int]]
      assertEquals(
        categories.get("Talk Radio"),
        Some(41),
        """Please use PekkoHttpServerInterpreter to declare the Tapir endpoint implementation to pekko-http routes:
          |} ~ PekkoHttpServerInterpreter().toRoute(…)""".stripMargin
      )
