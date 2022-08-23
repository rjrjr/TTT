package rjrjr.com.ttt.poetry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import rjrjr.com.ttt.framework.Presenter
import rjrjr.com.ttt.framework.UiModel
import rjrjr.com.ttt.poetry.StanzaPresenter.Output.ClosePoem
import rjrjr.com.ttt.poetry.StanzaPresenter.Output.ShowNextStanza
import rjrjr.com.ttt.poetry.StanzaPresenter.Output.ShowPreviousStanza
import rjrjr.com.ttt.poetry.StanzaPresenter.Props
import rjrjr.com.ttt.poetry.poetrydata.Poem
import rjrjr.com.ttt.poetry.poetrydata.PoemService

sealed class StanzaUi : UiModel

object LoadingStanza : StanzaUi()

data class ShowingStanza(
  val title: String,
  val poet: String,
  val stanzaNumber: Int,
  val lines: List<String>,
  val onGoUp: () -> Unit,
  val onGoBack: (() -> Unit)? = null,
  val onGoForth: (() -> Unit)? = null
) : StanzaUi()

class StanzaPresenter(
  private val service: PoemService
) : Presenter<Props, StanzaUi> {

  data class Props(
    val poemId: Int,
    val stanzaIndex: Int,
    val onOutput: (Output) -> Unit
  )

  enum class Output {
    ClosePoem,
    ShowPreviousStanza,
    ShowNextStanza
  }

  @Composable override fun present(props: Props): StanzaUi {
    var loading by remember { mutableStateOf(true) }
    var poemOrNull: Poem? by remember { mutableStateOf(null) }

    LaunchedEffect(props.poemId) {
      loading = true
      poemOrNull = service.getPoem(props.poemId)
      loading = false
    }

    return if (loading) LoadingStanza else poemOrNull!!.let { poem ->
      ShowingStanza(
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
}
