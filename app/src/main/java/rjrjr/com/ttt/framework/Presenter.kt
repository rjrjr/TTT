package rjrjr.com.ttt.framework

import androidx.compose.runtime.Composable

/**
 * Factory object that provides [Composable] access to [ModelT]
 * objects (view model _or whatever_) that reflect a system's current state.
 *
 * - Injection-friendly, because it is instance based.
 * - Molecule-friendly: not intended for use within a Compose UI tree, but rather
 *   in a parallel tree serving as a source of view models for one.
 *
 * [Presenter] instances should be immutable and interchangeable, injecting only
 * the services they need to produce their models. That is, be ready for callers
 * to create a new instance of your [Presenter] for each call to [present].
 *
 * All "instance" state **must** be managed via Compose,
 * via [remember][androidx.compose.runtime.remember],
 * [mutableStateOf][androidx.compose.runtime.mutableStateOf], etc.
 */
fun interface Presenter<in PropsT, out ModelT> {
  // It's unfortunate that implementers can't be forced to remember to include @Composable.
  // Is there a better way?
  @Composable fun present(props: PropsT): ModelT
}
