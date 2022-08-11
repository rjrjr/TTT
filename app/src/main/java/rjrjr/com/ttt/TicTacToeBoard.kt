package rjrjr.com.ttt

typealias TicTacToeBoard = List<List<Player?>>

val EMPTY_BOARD = listOf(
  listOf(null, null, null),
  listOf(null, null, null),
  listOf(null, null, null)
)

enum class Player {
  X,
  O;

  val other: Player
    get() = when (this) {
      X -> O
      O -> X
    }
}

fun TicTacToeBoard.hasVictory(): Boolean {
  var done = false

  // Across
  var row = 0
  while (!done && row < 3) {
    done =
      this[row][0] != null &&
        this[row][0] === this[row][1] &&
        this[row][0] === this[row][2]
    row++
  }

  // Down
  var col = 0
  while (!done && col < 3) {
    done =
      this[0][col] != null &&
        this[0][col] === this[1][col] &&
        this[0][col] === this[2][col]
    col++
  }

  // Diagonal
  done = done or (
    this[0][0] != null &&
      this[0][0] === this[1][1] &&
      this[0][0] === this[2][2]
    )

  done = done or (
    this[0][2] != null &&
      this[0][2] === this[1][1] &&
      this[0][2] === this[2][0]
    )

  return done
}

fun TicTacToeBoard.takeSquare(
  row: Int,
  col: Int,
  player: Player
): TicTacToeBoard {
  checkIndex(row)
  checkIndex(col)
  if (this[row][col] != null) return this

  val newRow: List<Player?> = this[row].toMutableList()
    .apply { this[col] = player }

  return toMutableList()
    .apply { this[row] = newRow }
}

private fun checkIndex(index: Int) {
  check(index in 0..2) { "Expected $index to be in 0..2" }
}
