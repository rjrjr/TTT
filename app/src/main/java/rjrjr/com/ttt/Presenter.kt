package rjrjr.com.ttt

import androidx.compose.runtime.Composable

/**
 * Injection-friendly factory object that provides [Composable] access to [ModelT]
 * objects (view model _or whatever_) that reflect a system's current state.
 * Molecule-friendly: not intended for use within a Compose UI tree, but rather
 * in a parallel tree serving as a source of view models for one.
 *
 * [Presenter] instances should be immutable and interchangeable, injecting only
 * the services they need to produce their models. That is, expect callers
 * to create a new instance of your [Presenter] for each call to [present].
 * All mutable state **must** be managed via Compose, via
 * [remember][androidx.compose.runtime.remember],
 * [mutableStateOf][androidx.compose.runtime.mutableStateOf], etc.
 */
fun interface Presenter<out ModelT> {
  @Composable fun present(): ModelT
}
