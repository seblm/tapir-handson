package podcast.infrastructure.api

import sttp.tapir._
import sttp.tapir.json.play._

class PodcastApi() {

  val getCategoriesEndPoint: Endpoint[Unit, Unit, Unit, Any] = ???

}
