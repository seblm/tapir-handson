package podcast.infrastructure.api

import org.scalatest.OptionValues._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers._
import sttp.model.Method.GET
import sttp.tapir.DecodeResult.Value
import sttp.tapir.EndpointIO.Body
import sttp.tapir.EndpointOutput.Pair

class PodcastApiSpec extends AnyFlatSpec {

  private lazy val podcastApi = new PodcastApi()

  "PodcastApi" should "define endpoint for getting all categories" in {
    podcastApi.getCategoriesEndPoint.info.name.value mustBe "all categories"
    podcastApi.getCategoriesEndPoint.info.description.value mustBe "get all categories and number of occurrences of podcasts for each categorie"
    podcastApi.getCategoriesEndPoint.info.summary.value mustBe "get all categories"
    podcastApi.getCategoriesEndPoint.info.tags must contain only "categorie"
  }

  it should "define http method for getting all categories" in {
    podcastApi.getCategoriesEndPoint.httpMethod.value mustBe GET
  }

  it should "define input as a simple path for getting all categories" in {
    podcastApi.getCategoriesEndPoint.input.show mustBe "GET /api /v1 /categories"
  }

  it should "define output as a JSON object for getting all categories" in {
    val output = podcastApi.getCategoriesEndPoint.output.asInstanceOf[Pair[_, _, _]].right
    val codec = output.asInstanceOf[Body[String, Map[String, Int]]].codec

    val decodedResult = codec.decode("""{"Talk Radio": 1, "": 8}""")

    val decoded = PartialFunction.condOpt(decodedResult) { case Value(categories) => categories }
    decoded.value mustBe Map("Talk Radio" -> 1, "" -> 8)
    podcastApi.getCategoriesEndPoint.output.show mustBe "{body as application/json (UTF-8)}"
  }

}
