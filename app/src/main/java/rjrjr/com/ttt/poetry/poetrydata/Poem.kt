package rjrjr.com.ttt.poetry.poetrydata

data class Poem(
  val title: String,
  val poet: Poet,
  val stanzas: List<List<String>>
) {
  /** The first line of each stanza. */
  val firstLines: List<String> by lazy {
    stanzas.map { lines -> lines[0].trim() }
  }

  override fun toString(): String {
    return "Poem($title)"
  }
}
