package podcast.infrastructure.csv

import podcast.domain.{Podcast, PodcastRepository}
import podcast.infrastructure.csv.CSVParser.csvLineToColumns

import java.net.URI
import java.time.LocalDate
import scala.io.Source
import scala.util.Try

class PodcastCSV(resourceName: String) extends PodcastRepository:

  private val podcasts: Seq[Podcast] = Source
    .fromResource(resourceName)
    .getLines()
    .drop(1)
    .map(csvLineToColumns)
    .flatMap(toPodcast)
    .toSeq

  val index: Seq[(Podcast, List[String])] = podcasts.map: podcast =>
    podcast -> (
      extractWords(podcast.userName) ++
        extractWords(podcast.userDescription) ++
        extractWords(podcast.docDescription) ++
        extractWords(podcast.docCategorie) ++
        extractWords(podcast.docDescripteurs) ++
        extractWords(podcast.docGenres) ++
        extractWords(podcast.docTypeProgramme) ++
        extractWords(podcast.docChaines) ++
        extractWords(podcast.docEmissions) ++
        extractWords(podcast.techAuteur) ++
        extractWords(podcast.techTitre)
    ).distinct

  private def extractWords(source: Option[String]): List[String] = source.fold[List[String]](List.empty): value =>
    value.toLowerCase.split("(?:\\s|,|\\\\n|\\.|\\(|\\)|:|/|\\*)+").toList

  private def toPodcast(columns: List[String]): Option[Podcast] =
    Some(
      Podcast(
        techId = columns.headOption,
        userName = Try(columns(1)).toOption,
        userDescription = Try(columns(2)).toOption,
        docDescription = Try(columns(3)).toOption,
        docCategorie = Try(columns(4)).filter(_.trim.nonEmpty).toOption,
        docDescripteurs = Try(columns(5)).toOption,
        docGenres = Try(columns(6)).toOption,
        docTypeProgramme = Try(columns(7)).toOption,
        docChaines = Try(columns(8)).toOption,
        docEmissions = Try(columns(9)).toOption,
        techDateEntreeCollecte = Try(columns(10)).flatMap(dateAsString => Try(LocalDate.parse(dateAsString))).toOption,
        techRssURL = Try(columns(11)).flatMap(urlAsString => Try(URI.create(urlAsString).toURL)).toOption,
        techRssURLRedirect = Try(columns(12)).flatMap(urlAsString => Try(URI.create(urlAsString).toURL)).toOption,
        techImage = Try(columns(13)).flatMap(urlAsString => Try(URI.create(urlAsString).toURL)).toOption,
        techLinkURL = Try(columns(14)).flatMap(urlAsString => Try(URI.create(urlAsString).toURL)).toOption,
        techAuteur = Try(columns(15)).toOption,
        techTitre = Try(columns(16)).toOption
      )
    )

  override def findById(id: String): Option[Podcast] = podcasts.find(_.techId.contains(id))

  override def search(query: String): Iterable[Podcast] = index.filter(_._2.contains(query.toLowerCase)).map(_._1)

  override def getCategories: Map[String, Int] = podcasts.groupBy(_.docCategorie).map {
    case (None, podcasts)            => ("", podcasts.length)
    case (Some(categorie), podcasts) => (categorie, podcasts.length)
  }

  override def findByCategorie(categorie: String): Iterable[Podcast] =
    podcasts.filter(_.docCategorie.contains(categorie))
