package rjrjr.com.ttt.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import rjrjr.com.ttt.ComposeUiScreenModel
import rjrjr.com.ttt.LocalScreenRegistry
import rjrjr.com.ttt.ScreenModel

/**
 * Displays a pair of [ScreenModel]s side by side in landscape, or stacked in portrait.
 */
@Immutable
data class SplitScreenModel(
  val firstScreen: ScreenModel,
  val secondScreen: ScreenModel
) : ComposeUiScreenModel {
  @Composable override fun Content() {
    SplitScreen(this)
  }
}

@Composable
private fun SplitScreen(model: SplitScreenModel) {
  BoxWithConstraints {
    val landscapeNow = maxWidth > maxHeight
    var landscape by remember { mutableStateOf(landscapeNow) }
    landscape = landscapeNow

    if (landscape) {
      Row { SplitScreenBody(model) }
    } else {
      Column { SplitScreenBody(model) }
    }
  }
}

@Composable
private fun SplitScreenBody(model: SplitScreenModel) {
  val screener = LocalScreenRegistry.current

  screener.ContentFor(model.firstScreen)
  screener.ContentFor(model.secondScreen)
}
