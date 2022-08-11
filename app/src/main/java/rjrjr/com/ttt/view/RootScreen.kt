package rjrjr.com.ttt.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import rjrjr.com.ttt.presenter.CounterModel
import rjrjr.com.ttt.presenter.EMPTY_BOARD
import rjrjr.com.ttt.presenter.Player.X
import rjrjr.com.ttt.presenter.RootModel
import rjrjr.com.ttt.presenter.TicTacToeModel

@Composable
fun RootScreen(root: RootModel) {
  BoxWithConstraints {
    val landscapeNow = maxWidth > maxHeight
    var landscape by remember { mutableStateOf(landscapeNow) }
    landscape = landscapeNow

    if (landscape) {
      Row { RootBody(root) }
    } else {
      Column { RootBody(root) }
    }
  }
}

@Composable
private fun RootBody(root: RootModel) {
  CounterScreen(root.first)
  TicTacToeScreen(root.second)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  val counter = CounterModel(1965, false, {}, {})
  val ticTacToe = TicTacToeModel(X, EMPTY_BOARD, { _, _ -> }, {})
  RootScreen(Pair(counter, ticTacToe))
}
