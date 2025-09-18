package rjrjr.com.ttt.poetry

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import rjrjr.com.ttt.framework.UiContentRegistry.Binding

val STANZA_BINDINGS = listOf(
  Binding(StanzaUi::class) { stanza ->
    Stanza(stanza)
  }
)

@Composable private fun Stanza(stanza: StanzaUi) {
  Column {
    Row { Text(text = stanza.title, fontWeight = FontWeight.ExtraBold) }
    Row { Text(text = stanza.poet, fontStyle = FontStyle.Italic) }
    stanza.lines.forEach { Text(it) }
    Row {
      Button(enabled = stanza.onGoBack != null, onClick = stanza.onGoBack ?: {}) { Text("<<") }
      Button(enabled = stanza.onGoForth != null, onClick = stanza.onGoForth ?: {}) { Text(">>") }
    }
  }

  stanza.onGoBack?.let {
    BackHandler(onBack = it)
  }
}
