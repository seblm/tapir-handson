package podcast.infrastructure.api

import org.apache.pekko
import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.model.{ContentTypes, HttpEntity}
import org.apache.pekko.http.scaladsl.server.Directives.{complete, get, path}
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object PodcastServer:

  private val logger = LoggerFactory.getLogger(getClass.getName.dropRight(1))

  @main def main(): Unit =
    implicit val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "podcast-server")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val route = path("index.html") {
      get {
        complete(
          HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            """<html>
              |<h1>Podcast API</h1>
              |</html>""".stripMargin
          )
        )
      }
    }
    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    logger.info(s"Server now online. Please navigate to http://localhost:8080/index.html")
    logger.info("Press RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
