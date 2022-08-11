package rjrjr.com.ttt.presenter

import androidx.compose.runtime.Composable
import rjrjr.com.ttt.Presenter
import rjrjr.com.ttt.ScreenModel
import rjrjr.com.ttt.view.SplitScreenModel

class RootPresenter(
  private val counterScreenPresenter: CounterScreenPresenter,
  private val ticTacToePresenter: TicTacToePresenter
) : Presenter<ScreenModel> {
  @Composable override fun present(): ScreenModel = SplitScreenModel(
    counterScreenPresenter.present(),
    ticTacToePresenter.present()
  )
}
