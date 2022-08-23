package rjrjr.com.ttt.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import rjrjr.com.ttt.ComposeUiModel
import rjrjr.com.ttt.LocalUiContentRegistry
import rjrjr.com.ttt.UiModel

/**
 * Displays a pair of [UiModel]s side by side in landscape, or stacked in portrait.
 */
data class SplitUi(
  val firstView: UiModel,
  val secondView: UiModel
) : ComposeUiModel {
  @Composable override fun Content() {
    SplitView(this)
  }
}

@Composable
private fun SplitView(model: SplitUi) {
  BoxWithConstraints {
    val landscapeNow = maxWidth > maxHeight
    var landscape by remember { mutableStateOf(landscapeNow) }
    landscape = landscapeNow

    if (landscape) {
      Row { SplitViewBody(model) }
    } else {
      Column { SplitViewBody(model) }
    }
  }
}

@Composable
private fun SplitViewBody(model: SplitUi) {
  val screener = LocalUiContentRegistry.current

  screener.ContentFor(model.firstView)
  screener.ContentFor(model.secondView)
}
