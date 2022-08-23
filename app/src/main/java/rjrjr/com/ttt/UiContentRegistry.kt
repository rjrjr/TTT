package rjrjr.com.ttt

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlin.reflect.KClass

/**
 * [CompositionLocal][androidx.compose.runtime.CompositionLocal] that can provide
 * Compose UI content for [UiModel] instances.
 */
class UiContentRegistry(private val bindings: List<Binding<*>> = emptyList()) {
  data class Binding<S : UiModel>(
    val type: KClass<S>,
    val content: @Composable (screenModel: S) -> Unit
  ) {
    @Composable fun Content(screen: UiModel) {
      @Suppress("UNCHECKED_CAST")
      content(screen as S)
    }
  }

  @Composable fun ContentFor(
    uiModel: UiModel,
    modifier: Modifier = Modifier
  ) {
    // We need to propagate min constraints because one of the likely uses for the modifier passed
    // into this function is to directly control the layout of the child view – which means
    // minimum constraints are likely to be significant.
    Box(modifier, propagateMinConstraints = true) {
      bindings.firstOrNull { it.type == uiModel::class }?.Content(uiModel)
      // bindings list gets first crack so that default ComposeScreenModel implementations
      // can be overridden.
        ?: (uiModel as? ComposeUiModel)?.Content()
        ?: Text(
          "I don't know what to do with this $uiModel! " +
            "Make it implement ComposeScreenModel, or provide a ScreenRegistry binding for it. " +
            "In real life we would throw an exception at this point."
        )
    }
  }

  override fun equals(other: Any?): Boolean =
    other === this || ((other as? UiContentRegistry)?.bindings == bindings)

  override fun hashCode(): Int = bindings.hashCode()
}
