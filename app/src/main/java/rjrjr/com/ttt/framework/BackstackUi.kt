package rjrjr.com.ttt.framework

import androidx.compose.runtime.Composable
import com.zachklipp.compose.backstack.Backstack

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

  private val frames by lazy {
    (previous + top)
  }

  @Composable override fun Content() {
    val map = frames.associate { it.key to it.ui }

    Backstack(
      map.keys.toList()
    ) { key -> ShowUi(map.getValue(key)) }
  }

  operator fun plus(other: BackstackUi<U>?): BackstackUi<U> {
    return if (other == null) this
    else BackstackUi(previous + top + other.previous, other.top)
  }
}

class UiAndKey<U : UiModel>(
  val ui: U,
  name: String = ""
) {
  val key = keyFor(ui, name)

  override fun equals(other: Any?): Boolean {
    return other is UiAndKey<*> && other.key == key
  }

  override fun hashCode(): Int {
    return key.hashCode()
  }

  companion object {
    fun keyFor(screen: UiModel, name: String): String =
      screen::class.qualifiedName!! +
        (name.takeUnless { it.isBlank() }?.let { "($it)" } ?: "")
  }
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
  require(isNotEmpty()) {
    "Cannot build a backstack from an empty list!"
  }
  return BackstackUi(
    previous = subList(0, size - 1).mapIndexed { i, u -> UiAndKey(u, getKey(i, u)) },
    top = last().let { UiAndKey(it, getKey(size - 1, it)) }
  )
}
