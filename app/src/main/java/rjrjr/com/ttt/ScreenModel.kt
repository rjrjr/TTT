package rjrjr.com.ttt

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import kotlin.reflect.KClass

/**
 * Marker interface for models that can be mapped to Compose UI content
 * by [ScreenRegistry.ContentFor].
 */
interface ScreenModel

/**
 * A [ScreenModel] that provides its own Compose UI [Content].
 * Convenient! Compile-time enforcement of model > view mapping!
 * Big time encapsulation violation, but a lot of times you just don't care!
 */
interface ComposeUiScreenModel : ScreenModel {
  @Composable fun Content()
}

val LocalScreenRegistry = compositionLocalOf { ScreenRegistry() }

/**
 * [CompositionLocal][androidx.compose.runtime.CompositionLocal] that can provide
 * Compose UI content for [ScreenModel] instances.
 */
class ScreenRegistry(private val bindings: List<Binding<*>> = emptyList()) {
  data class Binding<S : ScreenModel>(
    val type: KClass<S>,
    val content: @Composable (screenModel: S) -> Unit
  ) {
    @Composable fun Content(screen: ScreenModel) {
      @Suppress("UNCHECKED_CAST")
      content(screen as S)
    }
  }

  @Composable fun ContentFor(
    screenModel: ScreenModel,
    modifier: Modifier = Modifier
  ) {
    // We need to propagate min constraints because one of the likely uses for the modifier passed
    // into this function is to directly control the layout of the child view – which means
    // minimum constraints are likely to be significant.
    Box(modifier, propagateMinConstraints = true) {
      bindings.firstOrNull { it.type == screenModel::class }?.Content(screenModel)
      // bindings list gets first crack so that default ComposeScreenModel implementations
      // can be overridden.
        ?: (screenModel as? ComposeUiScreenModel)?.Content()
        ?: Text(
          "I don't know what to do with this $screenModel! " +
            "Make it implement ComposeScreenModel, or provide a ScreenRegistry binding for it. " +
            "In real life we would throw an exception at this point."
        )
    }
  }

  override fun equals(other: Any?): Boolean =
    other === this || ((other as? ScreenRegistry)?.bindings == bindings)

  override fun hashCode(): Int = bindings.hashCode()
}
