package podcast.infrastructure.api

import munit.FunSuite
import podcast.infrastructure.csv.PodcastCSV
import sttp.monad.FutureMonad
import sttp.tapir.DecodeResult.{Value, Failure as TapirDecodingFailure}
import sttp.tapir.EndpointIO.Body
import sttp.tapir.EndpointOutput.Pair
import sttp.tapir.{Codec, CodecFormat}

import scala.util.{Failure, Success, Try}

class PodcastApiSuite extends FunSuite:

  private val podcastApi = new PodcastApi(new PodcastCSV("depot-legal-du-web-liste-podcasts-10.csv"))

  test("PodcastApi should define a name for the endpoint that get all categories"):
    assert(podcastApi.getCategoriesEndPoint.info.name.isDefined, "A name definition is missing for the endpoint.")
    assertEquals(podcastApi.getCategoriesEndPoint.info.name.get, "all categories")

  test("PodcastApi should define a description for the endpoint that get all categories"):
    assert(podcastApi.getCategoriesEndPoint.info.description.isDefined, "A description is missing for the endpoint.")
    assertEquals(
      podcastApi.getCategoriesEndPoint.info.description.get,
      "get all categories and number of occurrences of podcasts for each categorie"
    )

  test("PodcastApi should define a summary for the endpoint that get all categories"):
    assert(podcastApi.getCategoriesEndPoint.info.summary.isDefined, "A summary is missing for the endpoint.")
    assertEquals(podcastApi.getCategoriesEndPoint.info.summary.get, "get all categories")

  test("PodcastApi should define a tag for the endpoint that get all categories"):
    assertEquals(podcastApi.getCategoriesEndPoint.info.tags, Vector("categorie"))

  test("PodcastApi should define http method for getting all categories"):
    assert(podcastApi.getCategoriesEndPoint.method.isDefined, "An http method is missing for the endpoint.")

  test("PodcastApi should define input as a simple path for getting all categories"):
    assertEquals(
      podcastApi.getCategoriesEndPoint.input.show,
      "GET /api /v1 /categories",
      """The simple path of your input is not correct. An input as a simple path could be defined as: endpoint.in("path" / "to" / "resource")"""
    )

  test("PodcastApi should define output as a JSON object for getting all categories"):
    val codec: Codec[String, Map[String, Int], CodecFormat] = Try:
      val output = podcastApi.getCategoriesEndPoint.output.asInstanceOf[Pair[?, ?, ?]]
      output.right.asInstanceOf[Body[String, Map[String, Int]]].codec
    match
      case Failure(exception) =>
        fail(
          s"The output definition is not a EndpointIO.Body[String, Map[String, Int]]. An output as a json body could be defined as: endpoint.out(jsonBody[ProducedType])",
          exception
        )
      case Success(value) => value

    val result = codec.decode("""{"TalkRadio": 1, "": 8}""")

    result match
      case failure: TapirDecodingFailure =>
        fail(s"The output definition is not a jsonBody[Map[String, Int]]: $failure.")
      case Value(categories) =>
        assertEquals(categories, Map("TalkRadio" -> 1, "" -> 8))
        assertEquals(podcastApi.getCategoriesEndPoint.output.show, "{body as application/json (UTF-8)}")

  test("PodcastApi should define server endpoint"):
    import scala.concurrent.ExecutionContext.Implicits.global
    assert(
      Try(podcastApi.getCategoriesServerEndpoint).isSuccess,
      """
        |Define server endpoint by calling serverLogic on getCategoriesEndPoint.""".stripMargin
    )
    podcastApi.getCategoriesServerEndpoint
      .logic(new FutureMonad()(using global))(())(())
      .map: value =>
        assertEquals(value.getOrElse(Map.empty), Map("" -> 8, "Talk Radio" -> 1))

  test("PodcastServer should use the Tapir's pekko-http server interpreter to expose endpoints"):
    assertEquals(
      compileErrors("import sttp.tapir.server.pekkohttp.PekkoHttpServerInterpreter"),
      "",
      """
        |In order for pekko-http to automatically convert ServerEndpoints to pekko http route, you have to import a new
        |dependency.
        |Please import "com.softwaremill.sttp.tapir" %% "tapir-pekko-http-server" % "1.12.2".
        |""".stripMargin
    )

  test("PodcastApi should expose endpoint with pekko-http"):
    import sys.process.*

    Try("curl --silent localhost:8080/api/v1/categories".!!) match

      case util.Failure(exception) =>
        fail("please start your server: main entry point is podcast.infrastructure.api.PodcastServer.main", exception)
      case util.Success(categories) =>
        assert(
          categories.contains(""","Talk Radio":41,""""),
          """Please use PekkoHttpServerInterpreter to declare the Tapir endpoint implementation to pekko-http routes:
            |} ~ PekkoHttpServerInterpreter.toRoute(…)""".stripMargin
        )
