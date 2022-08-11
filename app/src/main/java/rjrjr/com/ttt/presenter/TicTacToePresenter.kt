package rjrjr.com.ttt.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import rjrjr.com.ttt.Presenter
import rjrjr.com.ttt.presenter.Player.X

data class TicTacToeModel(
  val playing: Player,
  val board: TicTacToeBoard,
  val onTakeSquare: (row: Int, col: Int) -> Unit,
  val onReset: () -> Unit
)

object TicTacToePresenter : Presenter<TicTacToeModel> {
  @Composable
  override fun present(): TicTacToeModel {
    var board: TicTacToeBoard by remember { mutableStateOf(EMPTY_BOARD) }
    var playing by remember { mutableStateOf(X) }

    val gameOver = board.hasVictory()

    return TicTacToeModel(
      playing,
      board,
      onTakeSquare = if (gameOver) { _, _ -> } else { r, c ->
        board = board.takeSquare(r, c, playing)
        playing = playing.other
      },
      onReset = { board = EMPTY_BOARD }
    )
  }
}
