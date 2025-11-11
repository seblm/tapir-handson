package podcast.infrastructure.api

import podcast.domain.PodcastRepository
import sttp.tapir.*
import sttp.tapir.json.play.*
import sttp.tapir.server.ServerEndpoint.Full

import scala.concurrent.Future

class PodcastApi(repository: PodcastRepository):

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
