package podcast.infrastructure.api

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, get, path, pathSingleSlash}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object PodcastServer {

  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "podcast-server")
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

    println(s"Server online at http://localhost:8080/index.html\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

}
