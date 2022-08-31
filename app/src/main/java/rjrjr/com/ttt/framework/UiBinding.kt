package rjrjr.com.ttt.framework

import androidx.compose.runtime.Composable
import kotlin.reflect.KClass

/**
 * Entry registered with [ProvideLocalUiBindings], defining
 * what `@Composable` [content] function to use for a particular [type].
 */
data class UiBinding<S : UiModel>(
  val type: KClass<S>,
  val content: @Composable (screenModel: S) -> Unit
) {
  @Composable internal fun Content(screen: UiModel) {
    @Suppress("UNCHECKED_CAST")
    content(screen as S)
  }
}
