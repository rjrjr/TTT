package rjrjr.com.ttt.framework

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.layout.IntrinsicSize.Min
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextOverflow.Companion
import androidx.compose.ui.unit.dp

data class SimpleSelectListUi(
  val list: List<String>,
  val selection: Int,
  val onSelect: (Int) -> Unit
) : ComposeUiModel {
  @Composable override fun Content() {
    SimpleSelectList(list, selection, onSelect)
  }
}

@Composable private fun SimpleSelectList(
  list: List<String>,
  selection: Int,
  onSelect: (Int) -> Unit
) {
  Column(
    modifier = Modifier.selectableGroup()
      .verticalScroll(rememberScrollState())
  ) {
    list.forEachIndexed { index, string ->
      Row(
        Modifier
          .background(if (index == selection) MaterialTheme.colors.secondary else Color.Transparent)
          .padding(8.dp)
          .selectable(
          selected = index == selection,
          onClick = { onSelect(index) }
        ),
      ) {
        Text(
          text = string,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}
