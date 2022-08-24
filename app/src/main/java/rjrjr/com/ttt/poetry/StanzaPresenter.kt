package rjrjr.com.ttt.poetry

import androidx.compose.runtime.Composable
import rjrjr.com.ttt.framework.Presenter
import rjrjr.com.ttt.framework.UiModel
import rjrjr.com.ttt.poetry.StanzaPresenter.Output
import rjrjr.com.ttt.poetry.StanzaPresenter.Output.ClosePoem
import rjrjr.com.ttt.poetry.StanzaPresenter.Output.ShowNextStanza
import rjrjr.com.ttt.poetry.StanzaPresenter.Output.ShowPreviousStanza
import rjrjr.com.ttt.poetry.StanzaPresenter.Props
import rjrjr.com.ttt.poetry.poetrydata.Poem

data class StanzaUi(
  val title: String,
  val poet: String,
  val stanzaNumber: Int,
  val lines: List<String>,
  val onGoUp: () -> Unit,
  val onGoBack: (() -> Unit)? = null,
  val onGoForth: (() -> Unit)? = null
): UiModel

object StanzaPresenter : Presenter<Props, StanzaUi> {

  data class Props(
    val poem: Poem,
    val stanzaIndex: Int,
    val onOutput: (Output) -> Unit
  )

  enum class Output {
    ClosePoem,
    ShowPreviousStanza,
    ShowNextStanza
  }

  @Composable override fun present(props: Props): StanzaUi {
    val poem = props.poem
    return StanzaUi(
      title = poem.title,
      poet = poem.poet.fullName,
      stanzaNumber = props.stanzaIndex + 1,
      lines = poem.stanzas[props.stanzaIndex],
      onGoUp = { props.onOutput(ClosePoem) },
      onGoBack = { props.onOutput(ShowPreviousStanza) }.takeIf { props.stanzaIndex > 0 },
      onGoForth = { props.onOutput(ShowNextStanza) }.takeIf {
        props.stanzaIndex < poem.stanzas.size - 1
      },
    )
  }
}

@Composable fun StanzaPresenter.present(
   poem: Poem,
   stanzaIndex: Int,
   onOutput: (Output) -> Unit
): StanzaUi {
  return present(Props(poem, stanzaIndex, onOutput))
}
