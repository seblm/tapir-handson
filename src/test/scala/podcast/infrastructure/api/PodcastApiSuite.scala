package podcast.infrastructure.api

import munit.FunSuite

class PodcastApiSuite extends FunSuite:

  private val podcastApi = new PodcastApi()

  test("PodcastApi should define a name for the endpoint that get all categories"):
    assert(podcastApi.getCategoriesEndPoint.info.name.isDefined, "A name definition is missing for the endpoint.")
    assertEquals(podcastApi.getCategoriesEndPoint.info.name.get, "all categories")

  test("PodcastApi should define a description for the endpoint that get all categories"):
    assert(podcastApi.getCategoriesEndPoint.info.description.isDefined, "A description is missing for the endpoint.")
    assertEquals(
      podcastApi.getCategoriesEndPoint.info.description.get,
      "get all categories and number of occurrences of podcasts for each categorie"
    )

  test("PodcastApi should define a summary for the endpoint that get all categories"):
    assert(podcastApi.getCategoriesEndPoint.info.summary.isDefined, "A summary is missing for the endpoint.")
    assertEquals(podcastApi.getCategoriesEndPoint.info.summary.get, "get all categories")

  test("PodcastApi should define a tag for the endpoint that get all categories"):
    assertEquals(podcastApi.getCategoriesEndPoint.info.tags, Vector("categorie"))

  test("PodcastApi should define http method for getting all categories"):
    assert(podcastApi.getCategoriesEndPoint.method.isDefined, "An http method is missing for the endpoint.")

  test("PodcastApi should define input as a simple path for getting all categories"):
    assertEquals(
      podcastApi.getCategoriesEndPoint.input.show,
      "GET /api /v1 /categories",
      """The simple path of your input is not correct. An input as a simple path could be defined as: endpoint.in("path" / "to" / "resource")"""
    )

  test("tapir-json-play should be loaded in classpath"):
    assertEquals(
      compileErrors("import sttp.tapir.json.play.*"),
      "",
      """We need to produce JSON. For this we will use tapir-play-json. All that you'll need to do to use it for your
        |endpoint definition is to import http.tapir.json.play.* next.
        |
        |To fix this test you should import "com.softwaremill.sttp.tapir" %% "tapir-json-play" % "1.12.2" into your
        |build.sbt.
        |
        |""".stripMargin
    )
