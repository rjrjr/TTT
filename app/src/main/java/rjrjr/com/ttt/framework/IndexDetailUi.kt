package rjrjr.com.ttt.framework

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

data class IndexDetailUi<U : UiModel>(
  val index: U,
  val detail: U? = null
) : ComposeUiModel {
  @Composable override fun Content() {
    BoxWithConstraints {
      if (maxWidth > maxHeight) {
        Row {
          ShowUi(index, Modifier.weight(3f, true))
          detail?.let {
            ShowUi(it, Modifier.weight(7f, true))
          } ?: Box(Modifier.weight(7f, true))
        }
      } else {
        if (detail == null) {
          ShowUi(BackstackUi(index))
        } else {
          ShowUi(BackstackUi(index, detail))
        }
      }
    }
  }
}
