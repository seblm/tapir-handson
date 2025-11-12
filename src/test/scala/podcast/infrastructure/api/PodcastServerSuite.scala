package podcast.infrastructure.api

import com.github.pjfanning.pekkohttpplayjson.PlayJsonSupport
import munit.FunSuite
import org.apache.pekko.http.scaladsl.testkit.MUnitRouteTest

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

  test("PodcastApi should expose OpenAPI contract with swagger-ui"):
    Get("/docs/index.html") ~> PodcastServer.route ~> check:

      import org.apache.pekko.http.scaladsl.unmarshalling.Unmarshaller.given
      assert(
        responseAs[String].contains("""<title>Swagger UI</title>"""),
        "Swagger-UI can be served using `SwaggerInterpreter` with our existing endpoints, and added as a new route."
      )
