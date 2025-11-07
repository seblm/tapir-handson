package podcast.domain

trait PodcastRepository:

  def findById(id: String): Option[Podcast]

  def search(query: String): Iterable[Podcast]

  def getCategories: Map[String, Int]

  def findByCategorie(categorie: String): Iterable[Podcast]
