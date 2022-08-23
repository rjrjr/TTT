package rjrjr.com.ttt.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import rjrjr.com.ttt.UiContentRegistry.Binding
import rjrjr.com.ttt.presenter.LoadingStanza
import rjrjr.com.ttt.presenter.ShowingStanza

val STANZA_BINDINGS = listOf(
  Binding(LoadingStanza::class) {
    CircularProgressIndicator()
  },

  Binding(ShowingStanza::class) { stanza ->
    Column {
      Row {
        Text(stanza.title)
      }
      stanza.lines.forEach { Text(it) }
    }
  }
)
