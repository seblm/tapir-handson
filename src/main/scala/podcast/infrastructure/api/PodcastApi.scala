package podcast.infrastructure.api

import podcast.domain.PodcastRepository
import sttp.apispec.openapi.circe.yaml.*
import sttp.tapir.*
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.json.play.*
import sttp.tapir.server.ServerEndpoint.Full

import scala.concurrent.Future

class PodcastApi(repository: PodcastRepository):


  implicit val schema: Schema[Map[String, Int]] =
    Schema.schemaForMap[Int].description("Those are categories names with their counts")

  val getCategoriesEndPoint: Endpoint[Unit, Unit, Unit, Map[String, Int], Any] = endpoint
    .name("all categories")
    .description("get all categories and number of occurrences of podcasts for each categorie")
    .summary("get all categories")
    .tag("categorie")
    .get
    .in("api" / "v1" / "categories")
    .out(jsonBody[Map[String, Int]])

  lazy val getCategoriesServerEndpoint: Full[Unit, Unit, Unit, Unit, Map[String, Int], Any, Future] =
    getCategoriesEndPoint.serverLogic(_ => Future.successful(Right(repository.getCategories)))

  val yamlDocs: String = OpenAPIDocsInterpreter().toOpenAPI(getCategoriesEndPoint, "Podcast API", "v1").toYaml
