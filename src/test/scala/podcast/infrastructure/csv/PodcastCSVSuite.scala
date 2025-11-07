package podcast.infrastructure.csv

import munit.FunSuite

class PodcastCSVSuite extends FunSuite:

  private lazy val podcastCSV = new PodcastCSV("depot-legal-du-web-liste-podcasts-10.csv")

  test("PodcastCSV should find podcast by id"):
    val podcast = podcastCSV.findById("rfi-la_une_de_la_presse_cette_semaine_a_kinshasa")

    assertEquals(podcast.get.techId.get, "rfi-la_une_de_la_presse_cette_semaine_a_kinshasa")

  test("PodcastCSV should find podcast by full text search"):
    val results = podcastCSV.search("Presse")

    assert(results.map(_.techId.get).exists(_ == "rfi-la_une_de_la_presse_cette_semaine_a_kinshasa"))

  test("PodcastCSV should get all categories"):
    val categories = podcastCSV.getCategories

    assertEquals(categories, Map("Talk Radio" -> 1, "" -> 8))

  test("PodcastCSV should find podcast by categorie"):
    val results = podcastCSV.findByCategorie("Talk Radio")

    assert(results.map(_.techId.get).exists(_ == "RMC__Le_grand_oral_des_GG"))
