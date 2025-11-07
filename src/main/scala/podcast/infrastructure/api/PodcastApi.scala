package podcast.infrastructure.api

import sttp.tapir.*

class PodcastApi:

  val getCategoriesEndPoint: Endpoint[Unit, Unit, Unit, Unit, Any] = endpoint
