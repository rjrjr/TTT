package rjrjr.com.ttt.framework

import androidx.compose.runtime.Composable
import com.zachklipp.compose.backstack.Backstack

data class BackstackUi<U : UiModel>(
  val bottom: UiAndKey<U>,
  val rest: List<UiAndKey<U>>
) : ComposeUiModel {
  constructor(
    bottom: U,
    vararg rest: U
  ) : this(UiAndKey(bottom), rest.toList().map { UiAndKey(it) })

  val frames: List<UiAndKey<U>> = listOf(bottom) + rest

  @Composable override fun Content() {
    BackstackView(this)
  }

  operator fun plus(other: BackstackUi<U>?): BackstackUi<U> {
    return if (other == null) this
    else BackstackUi(frames[0], frames.subList(1, frames.size) + other.frames)
  }
}

data class UiAndKey<U : UiModel>(
  val ui: U,
  val key: String = keyFor(ui)
) {
  override fun equals(other: Any?): Boolean {
    return other is UiAndKey<*> && other.key == key
  }

  override fun hashCode(): Int {
    return key.hashCode()
  }

  companion object {
    fun keyFor(screen: UiModel): String = screen::class.qualifiedName!!
  }
}

@Composable
private fun BackstackView(model: BackstackUi<*>) {
  Backstack(model.frames) { ShowUi(it.ui) }
}

fun <U : UiModel> List<U>.toBackstackOrNull(
  getKey: (Int, U) -> String = { _, _ -> "" }
): BackstackUi<U>? = when {
  isEmpty() -> null
  else -> toBackstack(getKey)
}

fun <U : UiModel> List<U>.toBackstack(
  getKey: (Int, U) -> String = { _, _ -> "" }
): BackstackUi<U> {
  require(isNotEmpty())
  return BackstackUi(
    bottom = first().let { UiAndKey(it, getKey(0, it)) },
    rest = subList(1, size).mapIndexed { i, u -> UiAndKey(u, getKey(i + 1, u)) }
  )
}
