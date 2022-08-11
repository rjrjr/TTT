package rjrjr.com.ttt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import rjrjr.com.ttt.ui.theme.TTTTheme

@Composable
fun Counter(models: StateFlow<CounterModel>) {
  val model by models.collectAsState()
  Column {
    Text("This counts, right? ${model.value}")
    Row {
      Button(onClick = { model.onChange(1) }) { Text("Up") }
      Button(onClick = { model.onChange(-1) }) { Text("Down") }
      Button(onClick = { model.onRandomize() }) { Text("Rand") }
    }
    if (model.loading) Text("loading…")
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  TTTTheme {
    Counter(MutableStateFlow(CounterModel(1965, false, {}, {})))
  }
}
