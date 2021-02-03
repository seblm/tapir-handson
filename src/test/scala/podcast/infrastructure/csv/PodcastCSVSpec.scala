package podcast.infrastructure.csv

import org.scalatest.OptionValues._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class PodcastCSVSpec extends AnyFlatSpec with Matchers {

  private lazy val podcastCSV = new PodcastCSV("depot-legal-du-web-liste-podcasts-10.csv")

  "PodcastsCSV" should "find podcast by id" in {
    val podcast = podcastCSV.findById("rfi-la_une_de_la_presse_cette_semaine_a_kinshasa")

    podcast.value.techId.value mustBe "rfi-la_une_de_la_presse_cette_semaine_a_kinshasa"
  }

  it should "find podcast by full text search" in {
    val results = podcastCSV.search("Presse")

    results.map(_.techId.value) must contain("rfi-la_une_de_la_presse_cette_semaine_a_kinshasa")
  }

  it should "get all categories" in {
    val categories = podcastCSV.getCategories

    categories must contain only ("Talk Radio" -> 1, "" -> 8)
  }

  it should "find podcast by categorie" in {
    val results = podcastCSV.findByCategorie("Talk Radio")

    results.map(_.techId.value) must contain("RMC__Le_grand_oral_des_GG")
  }

}
