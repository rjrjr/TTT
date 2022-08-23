package rjrjr.com.ttt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import rjrjr.com.ttt.data.RandomService
import rjrjr.com.ttt.poetry.PoemService
import rjrjr.com.ttt.presenter.CounterPresenter
import rjrjr.com.ttt.presenter.StanzaPresenter
import rjrjr.com.ttt.presenter.StanzaPresenter.Output.ClosePoem
import rjrjr.com.ttt.presenter.StanzaPresenter.Output.ShowNextStanza
import rjrjr.com.ttt.presenter.StanzaPresenter.Output.ShowPreviousStanza
import rjrjr.com.ttt.presenter.TicTacToePresenter

class FakeDaggerWithNoSingletons {
  private fun randomService() = RandomService()

  private fun poemService(): PoemService = PoemService.AllPoems

  private fun counterPresenter() = CounterPresenter(randomService())

  private fun ticTacToePresenter() = TicTacToePresenter

  private fun stanzaPresenter() = StanzaPresenter(poemService())

  fun rootPresenter(): Presenter<Unit, UiModel> {
    // return RootPresenter(
    //   counterPresenter(),
    //   ticTacToePresenter()
    // )

    return object : Presenter<Unit, UiModel> {
      val stanzaPresenter = stanzaPresenter()

      @Composable override fun present(props: Unit): UiModel {
        var stanza by remember { mutableStateOf(0) }

        return stanzaPresenter.present(
          StanzaPresenter.Props(poemId = 0, stanzaIndex = stanza) {
            when (it) {
              ClosePoem -> {}
              ShowPreviousStanza -> stanza--
              ShowNextStanza -> stanza++
            }
          }
        )
      }
    }
  }
}
