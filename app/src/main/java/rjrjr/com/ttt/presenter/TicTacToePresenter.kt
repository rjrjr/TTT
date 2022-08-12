package rjrjr.com.ttt.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import rjrjr.com.ttt.Presenter
import rjrjr.com.ttt.ScreenModel
import rjrjr.com.ttt.presenter.Player.X

@Immutable
data class TicTacToeScreenModel(
  val playing: Player,
  val board: TicTacToeBoard,
  val onTakeSquare: (row: Int, col: Int) -> Unit,
  val onReset: () -> Unit
) : ScreenModel

object TicTacToePresenter : Presenter<TicTacToeScreenModel> {
  @Composable
  override fun present(): TicTacToeScreenModel {
    var board: TicTacToeBoard by remember { mutableStateOf(EMPTY_BOARD) }
    var playing by remember { mutableStateOf(X) }

    return TicTacToeScreenModel(
      playing,
      board,
      onTakeSquare = { r, c ->
        if (board.isGameOver()) return@TicTacToeScreenModel

        board = board.takeSquare(r, c, playing)
        if (!board.isGameOver()) playing = playing.other
      },
      onReset = { board = EMPTY_BOARD }
    )
  }
}
