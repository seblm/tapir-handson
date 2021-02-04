package podcast.infrastructure.api

import org.scalatest.OptionValues._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers._
import sttp.model.Method.GET

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

}
