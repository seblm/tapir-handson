package podcast.infrastructure.api

import sttp.tapir.*

class PodcastApi:

  val getCategoriesEndPoint: Endpoint[Unit, Unit, Unit, Unit, Any] = endpoint
    .name("all categories")
    .description("get all categories and number of occurrences of podcasts for each categorie")
    .summary("get all categories")
    .tag("categorie")
    .get
    .in("api" / "v1" / "categories")
