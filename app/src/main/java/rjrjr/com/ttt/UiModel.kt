package rjrjr.com.ttt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

/**
 * Marker interface for models that can be mapped to Compose UI content
 * by [UiContentRegistry.ContentFor].
 */
interface UiModel

/**
 * A [UiModel] that provides its own Compose UI [Content].
 * Convenient! Compile-time enforcement of model > view mapping!
 * Big time encapsulation violation, but a lot of times you just don't care!
 */
interface ComposeUiModel : UiModel {
  @Composable fun Content()
}

val LocalUiContentRegistry = compositionLocalOf { UiContentRegistry() }
