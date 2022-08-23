package rjrjr.com.ttt.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import rjrjr.com.ttt.Presenter
import rjrjr.com.ttt.UiModel
import rjrjr.com.ttt.presenter.Player.X

data class TicTacToeModel(
  val playing: Player,
  val board: TicTacToeBoard,
  val onTakeSquare: (row: Int, col: Int) -> Unit,
  val onReset: () -> Unit
) : UiModel

object TicTacToePresenter : Presenter<Unit, TicTacToeModel> {
  @Composable
  override fun present(props: Unit): TicTacToeModel {
    var board: TicTacToeBoard by remember { mutableStateOf(EMPTY_BOARD) }
    var playing by remember { mutableStateOf(X) }

    return TicTacToeModel(
      playing,
      board,
      onTakeSquare = { r, c ->
        if (board.isGameOver()) return@TicTacToeModel

        board = board.takeSquare(r, c, playing)
        if (!board.isGameOver()) playing = playing.other
      },
      onReset = { board = EMPTY_BOARD }
    )
  }
}
