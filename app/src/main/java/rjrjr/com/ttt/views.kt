package rjrjr.com.ttt

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import rjrjr.com.ttt.Player.X
import rjrjr.com.ttt.ui.theme.TTTTheme

typealias RootModel = Pair<CounterModel, TicTacToeModel>

@Composable
fun Root(roots: StateFlow<RootModel>) {
  val root by roots.collectAsState()

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
fun RootBody(root: RootModel) {
  Counter(root.first)
  TicTacToe(root.second)
}

@Composable
fun Counter(model: CounterModel) {
  Column {
    Text(
      if (model.loading) "…rolling the dice…" else "The count is ${model.value}"
    )
    Row {
      Button(onClick = { model.onChange(1) }) { Text("Up") }
      Button(onClick = { model.onChange(-1) }) { Text("Down") }
      Button(onClick = { model.onRandomize() }) { Text("Rand") }
    }

  }
}

@Composable
fun TicTacToe(model: TicTacToeModel) {
  Column {
    for (row in 0..2)
      Row {
        for (col in 0..2)
          Button(
            onClick = { model.onTakeSquare(row, col) }
          ) {
            Text(model.board[row][col]?.name ?: "")
          }
      }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  TTTTheme {
    val counter = CounterModel(1965, false, {}, {})
    val ticTacToe = TicTacToeModel(X, EMPTY_BOARD) { _, _ -> }
    Root(MutableStateFlow(Pair(counter, ticTacToe)))
  }
}
