package rjrjr.com.ttt.counter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun Counter(model: CounterUi) {
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
