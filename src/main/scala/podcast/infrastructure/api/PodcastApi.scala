package podcast.infrastructure.api

import podcast.domain.PodcastRepository
import sttp.tapir._
import sttp.tapir.json.play._

class PodcastApi(val repository: PodcastRepository) {

  val getCategoriesEndPoint: Endpoint[Unit, Unit, Map[String, Int], Any] = endpoint
    .name("all categories")
    .description("get all categories and number of occurrences of podcasts for each categorie")
    .summary("get all categories")
    .tag("categorie")
    .get
    .in("api" / "v1" / "categories")
    .out(anyJsonBody[Map[String, Int]])

}
