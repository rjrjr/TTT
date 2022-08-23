package rjrjr.com.ttt.poetry

private const val WIKIPEDIA = "https://en.wikipedia.org/wiki/"

data class Poet(
  val fullName: String,
  val bioUrl: String
) {
  companion object {
    val Blake = Poet("William Blake", WIKIPEDIA + "William_Blake")
    val Poe = Poet("Edgar Allan Poe", WIKIPEDIA + "Edgar_Allan_Poe")
  }
}
