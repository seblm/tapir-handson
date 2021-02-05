package podcast.infrastructure.api

import podcast.domain.PodcastRepository
import sttp.tapir._
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.json.play._
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.openapi.OpenAPI
import sttp.tapir.server.ServerEndpoint

import scala.concurrent.Future

class PodcastApi(val repository: PodcastRepository) {

  val getCategoriesEndPoint: Endpoint[Unit, Unit, Map[String, Int], Any] = endpoint
    .name("all categories")
    .description("get all categories and number of occurrences of podcasts for each categorie")
    .summary("get all categories")
    .tag("categorie")
    .get
    .in("api" / "v1" / "categories")
    .out(anyJsonBody[Map[String, Int]])

  val getCategoriesEndPointServer: ServerEndpoint[Unit, Unit, Map[String, Int], Any, Future] =
    getCategoriesEndPoint.serverLogic[Future](_ => Future.successful(Right(repository.getCategories)))

  private val openAPI: OpenAPI =
    OpenAPIDocsInterpreter.toOpenAPI(getCategoriesEndPoint, "Podcast API", "0.1.0-SNAPSHOT")

  val yamlDocs: String = openAPI.toYaml

}
