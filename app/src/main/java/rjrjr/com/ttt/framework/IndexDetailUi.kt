package rjrjr.com.ttt.framework

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class IndexDetailUi<U : UiModel>(
  val index: BackstackUi<U>,
  val detail: BackstackUi<U>? = null
) : ComposeUiModel {
  @Composable override fun Content() {
    BoxWithConstraints {
      if (maxWidth > maxHeight) {
        Row {
          ShowUi(index, Modifier.weight(3f, true))
          detail?.let {
            ShowUi(it, Modifier.weight(7f, true))
          }
        }
      } else {
        ShowUi(index + detail)
      }
    }
  }

  operator fun plus(other: IndexDetailUi<U>): IndexDetailUi<U> {
    val newIndex = index + other.index
    val newDetail = detail?.let { it + other.detail } ?: other.detail

    return IndexDetailUi(newIndex, newDetail)
  }
}

