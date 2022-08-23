package rjrjr.com.ttt.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import rjrjr.com.ttt.presenter.CounterModel

@Composable
fun CounterView(model: CounterModel) {
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
