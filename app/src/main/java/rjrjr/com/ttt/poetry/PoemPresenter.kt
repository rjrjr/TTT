package rjrjr.com.ttt.poetry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import rjrjr.com.ttt.framework.BackstackUi
import rjrjr.com.ttt.framework.IndexDetailUi
import rjrjr.com.ttt.framework.LoadingUi
import rjrjr.com.ttt.framework.Presenter
import rjrjr.com.ttt.framework.SimpleSelectListUi
import rjrjr.com.ttt.framework.UiModel
import rjrjr.com.ttt.framework.toBackstack
import rjrjr.com.ttt.poetry.PoemPresenter.Props
import rjrjr.com.ttt.poetry.StanzaPresenter.Output.ClosePoem
import rjrjr.com.ttt.poetry.StanzaPresenter.Output.ShowNextStanza
import rjrjr.com.ttt.poetry.StanzaPresenter.Output.ShowPreviousStanza
import rjrjr.com.ttt.poetry.poetrydata.Poem
import rjrjr.com.ttt.poetry.poetrydata.PoemService

class PoemPresenter(
  private val service: PoemService,
  private val stanzaPresenter: StanzaPresenter
) : Presenter<Props, UiModel> {

  data class Props(
    val poemId: Int
  )

  @Composable override fun present(props: Props): UiModel {
    var loading by remember { mutableStateOf(true) }
    var poemOrNull: Poem? by remember { mutableStateOf(null) }
    var showingStanza by remember { mutableStateOf(0) }

    LaunchedEffect(props.poemId) {
      loading = true
      poemOrNull = service.getPoem(props.poemId)
      loading = false
    }

    return if (loading) LoadingUi else poemOrNull!!.let { poem ->
      val stanzaUis = (0..showingStanza).map { index ->
        stanzaPresenter.present(poem, index) {
          when (it) {
            ClosePoem -> {}
            ShowPreviousStanza -> showingStanza--
            ShowNextStanza -> showingStanza++
          }
        }
      }

      IndexDetailUi(
        index = BackstackUi(
          SimpleSelectListUi(poem.firstLines, showingStanza) {
            showingStanza = it
          }
        ),
        detail = stanzaUis.toBackstack { i, _ -> "$i" }
      )
    }
  }
}
