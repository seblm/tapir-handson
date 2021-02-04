package podcast.infrastructure.api

import sttp.tapir._

class PodcastApi() {

  val getCategoriesEndPoint = endpoint
    .name("all categories")
    .description("get all categories and number of occurrences of podcasts for each categorie")
    .summary("get all categories")
    .tag("categorie")
    .get
    .in("api" / "v1" / "categories")

}
