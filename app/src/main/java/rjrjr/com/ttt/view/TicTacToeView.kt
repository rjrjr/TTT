package rjrjr.com.ttt.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import rjrjr.com.ttt.presenter.EMPTY_BOARD
import rjrjr.com.ttt.presenter.TicTacToeModel
import rjrjr.com.ttt.presenter.hasVictory
import rjrjr.com.ttt.presenter.isFull

@Composable
fun TicTacToeView(model: TicTacToeModel) {
  Column {
    for (row in 0..2) {
      Row {
        for (col in 0..2)
          Button(
            onClick = { model.onTakeSquare(row, col) },
            enabled = !model.board.hasVictory()
          ) {
            Text(model.board[row][col]?.name ?: "")
          }
      }
    }
    Row {
      Button(
        onClick = model.onReset,
        enabled = model.board != EMPTY_BOARD
      ) {
        Text("Reset")
      }

      if (model.board.hasVictory()) Text("${model.playing} wins!")
      else if (model.board.isFull()) Text("Draw")
    }
  }
}
