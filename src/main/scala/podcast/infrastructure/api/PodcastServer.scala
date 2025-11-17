package podcast.infrastructure.api

import org.apache.pekko
import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.model.{ContentTypes, HttpEntity}
import org.apache.pekko.http.scaladsl.server.Directives.*
import org.apache.pekko.http.scaladsl.server.Route
import org.apache.pekko.http.scaladsl.server.RouteConcatenation.given
import org.slf4j.LoggerFactory
import podcast.infrastructure.csv.PodcastCSV
import sttp.tapir.redoc.RedocUIOptions
import sttp.tapir.redoc.bundle.RedocInterpreter
import sttp.tapir.server.pekkohttp.PekkoHttpServerInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object PodcastServer:

  private val logger = LoggerFactory.getLogger(getClass.getName.dropRight(1))

  implicit val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "podcast-server")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  val api = new PodcastApi(new PodcastCSV("depot-legal-du-web-liste-podcasts.csv"))
  private val swaggerUI =
    SwaggerInterpreter().fromServerEndpoints(List(api.getCategoriesServerEndpoint), "Podcast API", "0.1.0-SNAPSHOT")

  val route: Route = path("index.html"):
    get:
      complete(
        HttpEntity(
          ContentTypes.`text/html(UTF-8)`,
          """<html>
            |<h1>Podcast API</h1>
            |<p><a href="/docs/index.html">Swagger UI</a></p>
            |<p><a href="/redoc/index.html">Redoc</a></p>
            |</html>""".stripMargin
        )
      )
  ~ PekkoHttpServerInterpreter().toRoute(api.getCategoriesServerEndpoint +: swaggerUI)

  @main def main(): Unit =
    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    logger.info(s"Server now online. Please navigate to http://localhost:8080/index.html")
    logger.info("Press RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
