package podcast.domain

import java.net.URL
import java.time.LocalDate

case class Podcast(
    techId: Option[String], // varchar 100
    userName: Option[String], // varchar 400
    userDescription: Option[String], // text
    docDescription: Option[String], // text
    docCategorie: Option[String], // varchar 100
    docDescripteurs: Option[String], // varchar 500
    docGenres: Option[String], // varchar 300
    docTypeProgramme: Option[String], // varchar 300
    docChaines: Option[String], // varchar 200
    docEmissions: Option[String], // varchar 400
    techDateEntreeCollecte: Option[LocalDate], // text
    techRssURL: Option[URL], // text
    techRssURLRedirect: Option[URL], // text
    techImage: Option[URL], // text
    techLinkURL: Option[URL], // text
    techAuteur: Option[String], // text
    techTitre: Option[String] // text
)
