package rjrjr.com.ttt.presenter

import androidx.compose.runtime.Composable
import rjrjr.com.ttt.Presenter
import rjrjr.com.ttt.UiModel
import rjrjr.com.ttt.view.SplitModel

class RootPresenter(
  private val counterPresenter: CounterPresenter,
  private val ticTacToePresenter: TicTacToePresenter
) : Presenter<Unit, UiModel> {
  @Composable override fun present(props: Unit): UiModel = SplitModel(
    counterPresenter.present(Unit),
    ticTacToePresenter.present(Unit)
  )
}
