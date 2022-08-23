package rjrjr.com.ttt.view

import androidx.compose.runtime.Composable
import com.zachklipp.compose.backstack.Backstack
import rjrjr.com.ttt.ComposeUiModel
import rjrjr.com.ttt.LocalUiContentRegistry
import rjrjr.com.ttt.UiModel

data class BackstackUi(
  val bottom: UiModelAndKey,
  val rest: List<UiModelAndKey>
) : ComposeUiModel {
  constructor(
    bottom: UiModel,
    vararg rest: UiModel
  ) : this(UiModelAndKey(bottom), rest.toList().map { UiModelAndKey(it) })

  val frames: List<UiModelAndKey> = listOf(bottom) + rest

  @Composable override fun Content() {
    BackstackView(this)
  }
}

data class UiModelAndKey(
  val uiModel: UiModel,
  val key: String = keyFor(uiModel)
) {
  override fun equals(other: Any?): Boolean {
    return other is UiModelAndKey && other.key == key
  }

  override fun hashCode(): Int {
    return key.hashCode()
  }

  companion object {
    fun keyFor(screen: UiModel): String = screen::class.qualifiedName!!
  }
}

@Composable
private fun BackstackView(model: BackstackUi) {
  Backstack(model.frames) { LocalUiContentRegistry.current.ContentFor(it.uiModel) }
}
