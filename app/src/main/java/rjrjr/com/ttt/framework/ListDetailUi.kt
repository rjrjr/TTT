package rjrjr.com.ttt.framework

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class ListDetailUi<U : UiModel>(
  val list: BackstackUi<U>,
  val detail: BackstackUi<U>? = null
) : ComposeUiModel {
  @Composable override fun Content() {
    BoxWithConstraints {
      if (maxWidth > maxHeight) {
        Row {
          ShowUi(list, Modifier.weight(3f, true))
          Box(Modifier.weight(7f, true)) {
            detail?.let { ShowUi(it) }
          }
        }
      } else {
        ShowUi(list + detail)
      }
    }
  }

  operator fun plus(other: ListDetailUi<U>): ListDetailUi<U> {
    val newIndex = list + other.list
    val newDetail = detail?.let { it + other.detail } ?: other.detail

    return ListDetailUi(newIndex, newDetail)
  }
}

