package rjrjr.com.ttt.tictactoe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import rjrjr.com.ttt.framework.Presenter
import rjrjr.com.ttt.framework.UiModel
import rjrjr.com.ttt.tictactoe.Player.X

data class TicTacToeUi(
  val playing: Player,
  val board: TicTacToeBoard,
  val onTakeSquare: (row: Int, col: Int) -> Unit,
  val onReset: () -> Unit
) : UiModel

object TicTacToePresenter : Presenter<Unit, TicTacToeUi> {
  @Composable
  override fun present(props: Unit): TicTacToeUi {
    var board: TicTacToeBoard by remember { mutableStateOf(EMPTY_BOARD) }
    var playing by remember { mutableStateOf(X) }

    return TicTacToeUi(
      playing,
      board,
      onTakeSquare = { r, c ->
        if (board.isGameOver()) return@TicTacToeUi

        board = board.takeSquare(r, c, playing)
        if (!board.isGameOver()) playing = playing.other
      },
      onReset = { board = EMPTY_BOARD }
    )
  }
}
