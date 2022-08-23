package rjrjr.com.ttt

import androidx.compose.runtime.Composable
import rjrjr.com.ttt.counter.CounterPresenter
import rjrjr.com.ttt.framework.Presenter
import rjrjr.com.ttt.framework.SplitUi
import rjrjr.com.ttt.framework.UiModel
import rjrjr.com.ttt.tictactoe.TicTacToePresenter


class RootPresenter(
  private val counterPresenter: CounterPresenter,
  private val ticTacToePresenter: TicTacToePresenter
) : Presenter<Unit, UiModel> {
  @Composable override fun present(props: Unit): UiModel = SplitUi(
    counterPresenter.present(Unit),
    TicTacToePresenter.present(Unit)
  )
}
