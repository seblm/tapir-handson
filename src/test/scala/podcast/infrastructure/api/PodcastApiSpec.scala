package podcast.infrastructure.api

import org.scalatest.OptionValues._
import org.scalatest.TryValues._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers._
import podcast.infrastructure.csv.PodcastCSV
import sttp.model.Method.GET
import sttp.tapir.DecodeResult.Value
import sttp.tapir.EndpointIO.Body
import sttp.tapir.EndpointOutput.Pair

import scala.util.Try

class PodcastApiSpec extends AnyFlatSpec {

  private lazy val podcastApi = new PodcastApi(new PodcastCSV("depot-legal-du-web-liste-podcasts-10.csv"))

  "PodcastApi" should "define endpoint for getting all categories" in {
    podcastApi.getCategoriesEndPoint.info.name.value mustBe "all categories"
    podcastApi.getCategoriesEndPoint.info.description.value mustBe "get all categories and number of occurrences of podcasts for each categorie"
    podcastApi.getCategoriesEndPoint.info.summary.value mustBe "get all categories"
    podcastApi.getCategoriesEndPoint.info.tags must contain only "categorie"
  }

  it should "define http method for getting all categories" in {
    podcastApi.getCategoriesEndPoint.httpMethod.value mustBe GET
  }

  it should "define input as a simple path for getting all categories" in {
    podcastApi.getCategoriesEndPoint.input.show mustBe "GET /api /v1 /categories"
  }

  it should "define output as a JSON object for getting all categories" in {
    val output = podcastApi.getCategoriesEndPoint.output.asInstanceOf[Pair[_, _, _]].right
    val codec = output.asInstanceOf[Body[String, Map[String, Int]]].codec

    val decodedResult = codec.decode("""{"Talk Radio": 1, "": 8}""")

    val decoded = PartialFunction.condOpt(decodedResult) { case Value(categories) => categories }
    decoded.value mustBe Map("Talk Radio" -> 1, "" -> 8)
    podcastApi.getCategoriesEndPoint.output.show mustBe "{body as application/json (UTF-8)}"
  }

  it should "expose endpoint with akka-http" in {
    import sys.process._

    withClue("please start your server: main class is podcast.infrastructure.api.PodcastServer") {
      val categories = Try("curl --silent localhost:8080/api/v1/categories".!!).success.value

      categories must startWith("""{"""")
      categories must endWith("}\n")
      categories must include(""","Talk Radio":41,"""")
    }
  }

  it should "generate openapi contract" in {
    podcastApi.yamlDocs mustBe
      """openapi: 3.0.3
        |info:
        |  title: Podcast API
        |  version: 0.1.0-SNAPSHOT
        |paths:
        |  /api/v1/categories:
        |    get:
        |      tags:
        |      - categorie
        |      summary: get all categories
        |      description: get all categories and number of occurrences of podcasts for each
        |        categorie
        |      operationId: all categories
        |      responses:
        |        '200':
        |          description: ''
        |          content:
        |            application/json:
        |              schema:
        |                $ref: '#/components/schemas/Map_Int'
        |components:
        |  schemas:
        |    Map_Int:
        |      type: object
        |      additionalProperties:
        |        type: integer
        |""".stripMargin
  }

}
