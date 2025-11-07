package podcast.infrastructure.csv

import podcast.infrastructure.csv.CSVParser.State.{Column, Start}

import scala.annotation.tailrec

object CSVParser:

  enum State:
    case Start, Column

  def csvLineToColumns(line: String): List[String] = csvLineToColumns(line, List.empty, Start)

  @tailrec
  private def csvLineToColumns(line: String, columns: List[String], state: State): List[String] = state match
    case Start if line.isEmpty             => columns.reverse
    case Start if line.startsWith(",")     => csvLineToColumns(line.tail, columns, Start)
    case Start if line.startsWith("\"")    => csvLineToColumns(line.tail, "" :: columns, Column)
    case Start                             => List.empty
    case Column if line.startsWith("\"\"") =>
      csvLineToColumns(line.drop(2), columns.head + line.head :: columns.tail, Column)
    case Column if line.startsWith("\"") => csvLineToColumns(line.tail, columns, Start)
    case Column => csvLineToColumns(line.tail, columns.head + line.head :: columns.tail, Column)
