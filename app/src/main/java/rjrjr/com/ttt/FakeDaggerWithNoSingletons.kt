package rjrjr.com.ttt

import rjrjr.com.ttt.data.RandomService
import rjrjr.com.ttt.presenter.CounterPresenter
import rjrjr.com.ttt.presenter.RootPresenter
import rjrjr.com.ttt.presenter.TicTacToePresenter

class FakeDaggerWithNoSingletons {
  private fun randomService() = RandomService()

  private fun counterPresenter() = CounterPresenter(randomService())

  private fun ticTacToePresenter() = TicTacToePresenter

  fun rootPresenter(): RootPresenter {
    return RootPresenter(
      counterPresenter(),
      ticTacToePresenter()
    )
  }
}
