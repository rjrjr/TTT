package rjrjr.com.ttt.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontStyle.Companion
import androidx.compose.ui.text.font.FontWeight
import rjrjr.com.ttt.UiContentRegistry.Binding
import rjrjr.com.ttt.presenter.LoadingStanza
import rjrjr.com.ttt.presenter.ShowingStanza

val STANZA_BINDINGS = listOf(
  Binding(LoadingStanza::class) {
    CircularProgressIndicator()
  },

  Binding(ShowingStanza::class) { stanza ->
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
)
