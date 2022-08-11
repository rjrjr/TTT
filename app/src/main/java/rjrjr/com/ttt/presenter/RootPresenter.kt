package rjrjr.com.ttt.presenter

import androidx.compose.runtime.Composable
import rjrjr.com.ttt.Presenter

typealias RootModel = Pair<CounterModel, TicTacToeModel>

class RootPresenter(
  private val counterPresenter: CounterPresenter,
  private val ticTacToePresenter: TicTacToePresenter
) : Presenter<RootModel> {
  @Composable override fun present(): RootModel = Pair(
    counterPresenter.present(),
    ticTacToePresenter.present()
  )
}
