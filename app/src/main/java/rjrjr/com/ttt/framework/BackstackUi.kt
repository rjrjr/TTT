package rjrjr.com.ttt.framework

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay

data class BackstackUi<U : UiModel>(
  val previous: List<UiAndKey<U>> = emptyList(),
  val top: UiAndKey<U>
) : ComposeUiModel {
  constructor(
    bottom: U,
    vararg more: U
  ) : this(
    previous = (listOf(bottom) + more.toList()).subList(0, more.size).map { UiAndKey(it) },
    top = more.lastOrNull()?.let { UiAndKey(it) } ?: UiAndKey(bottom)
  )

  val frames: List<UiAndKey<U>> = previous + top

  @Composable override fun Content() {
    BackstackView(this)
  }

  operator fun plus(other: BackstackUi<U>?): BackstackUi<U> {
    return if (other == null) this
    else BackstackUi(previous + top + other.previous, other.top)
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
  NavDisplay(
    model.frames,
    onBack = {
      // Our habit is to leave this kind of thing a no-op to discourage
      // screen authors from relying on magical default pop behavior.
      // Maybe rethink that?
    },
    entryProvider = { uiAndKey ->
      NavEntry(uiAndKey) {
        ShowUi(uiAndKey.ui)
      }
    }
  )
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
    previous = subList(0, size - 1).mapIndexed { i, u -> UiAndKey(u, getKey(i, u)) },
    top = last().let { UiAndKey(it, getKey(size - 1, it)) }
  )
}
