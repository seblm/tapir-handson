package podcast.infrastructure.api

import org.apache.pekko
import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.model.{ContentTypes, HttpEntity}
import org.apache.pekko.http.scaladsl.server.Directives.*
import org.apache.pekko.http.scaladsl.server.RouteConcatenation.given
import org.slf4j.LoggerFactory
import podcast.infrastructure.csv.PodcastCSV
import sttp.model.headers.WWWAuthenticateChallenge
import sttp.tapir.model.UsernamePassword
import sttp.tapir.redoc.RedocUIOptions
import sttp.tapir.redoc.bundle.RedocInterpreter
import sttp.tapir.server.pekkohttp.PekkoHttpServerInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.{auth, endpoint, stringBody, stringToPath}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn

object PodcastServer:

  private val logger = LoggerFactory.getLogger(getClass.getName.dropRight(1))

  @main def main(): Unit =
    implicit val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "podcast-server")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val api = new PodcastApi(new PodcastCSV("depot-legal-du-web-liste-podcasts.csv"))
    val swaggerEndpoints = SwaggerInterpreter()
      .fromEndpoints[Future](List(api.getCategoriesEndPoint), "Podcast API", "1.0") // /docs
    val redocEndpoints = RedocInterpreter(redocUIOptions = RedocUIOptions.default.copy(pathPrefix = List("redoc")))
      .fromEndpoints[Future](List(api.getCategoriesEndPoint), "Podcast API Redoc", "1.0")

    // Security
    val secretEndpoint =
      endpoint.get.securityIn("secret").securityIn(
        auth.basic[UsernamePassword](WWWAuthenticateChallenge.basic("example"))).out(stringBody)
    val secretServerEndpoint = secretEndpoint
        .serverSecurityLogic(credentials => Future.successful(Right(credentials.username)))
        .serverLogic(username => _ => Future.successful(Right(s"Hello, $username!")))


    val route = path("index.html") {
      get {
        complete(
          HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            """<html>
              |<h1>Podcast API</h1>
              |<a href="/docs"> - Swagger UI</a> <br/>
              |<a href="/redoc"> - Redoc</a> <br/>
              |<a href="/secret"> - Security</a>
              |</html>""".stripMargin
          )
        )
      }
    } ~ PekkoHttpServerInterpreter().toRoute(
      List(api.getCategoriesServerEndpoint) ++ swaggerEndpoints ++ redocEndpoints ++ List(secretServerEndpoint))
    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    logger.info(s"Server now online. Please navigate to http://localhost:8080/index.html")
    logger.info("Press RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
