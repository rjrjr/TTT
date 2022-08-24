package rjrjr.com.ttt.framework

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable

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
    val landscape = maxWidth > maxHeight

    if (landscape) {
      Row { SplitViewBody(model) }
    } else {
      Column { SplitViewBody(model) }
    }
  }
}

@Composable
private fun SplitViewBody(model: SplitUi) {
  ShowUi(uiModel = model.firstView)
  ShowUi(uiModel = model.secondView)
}
