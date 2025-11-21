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

  test("PodcastApi should expose OpenAPI contract with redoc"):
    Get("/redoc/index.html") ~> PodcastServer.route ~> check:

      import org.apache.pekko.http.scaladsl.unmarshalling.Unmarshaller.given
      assert(
        responseAs[String].contains("<redoc "),
        """Likewise, Redoc can be used with `RedocInterpreter`, but this time let’s put it on a specific route.
          |Change `pathPrefix` of the `redocUIOptions` parameter.""".stripMargin
      )

  test("PodcastApi should expose a secured endpoint"):
    Get("/secret") ~> PodcastServer.route ~> check:

      import org.apache.pekko.http.scaladsl.unmarshalling.Unmarshaller.given
      assert(
        responseAs[String].contains("Invalid value for: header Authorization (missing)"),
        """Tapir also allows us to secure our endpoints. We’ll be using a very crude and outdated way to do it.
            |We’ll have to define:
            |- The authentification used:
            |- The logic to apply after authentication""".stripMargin
      )

  test("PodcastApi should have a customized schema"):
    Get("/docs/docs.yaml") ~> PodcastServer.route ~> check:

      import org.apache.pekko.http.scaladsl.unmarshalling.Unmarshaller.given
      assert(
        responseAs[String].contains("""Those are categories names with their counts"""),
        """It is possible to add customization to the auto-generated schema.
          |For example, let’s add a description to the main parameter.""".stripMargin
      )
