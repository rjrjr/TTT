package rjrjr.com.ttt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.AndroidUiDispatcher.Companion.Main
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.plus
import rjrjr.com.ttt.ScreenRegistry.Binding
import rjrjr.com.ttt.presenter.CounterScreenModel
import rjrjr.com.ttt.presenter.TicTacToeScreenModel
import rjrjr.com.ttt.ui.theme.TTTTheme
import rjrjr.com.ttt.view.CounterScreen
import rjrjr.com.ttt.view.TicTacToeScreen

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val continuity: Continuity by viewModels()
    val models = continuity.models

    setContent { AppUi(models) }
  }

  class Continuity : ViewModel() {
    private val scope = viewModelScope + Main
    val models = scope.launchMolecule(clock = RecompositionClock.ContextClock) {
      val rootPresenter = remember { FakeDaggerWithNoSingletons().rootPresenter() }
      rootPresenter.present()
    }
  }
}

@Composable
fun AppUi(roots: StateFlow<ScreenModel>) {
  val root by roots.collectAsState()

  CompositionLocalProvider(
    LocalScreenRegistry provides ScreenRegistry(
      listOf(
        Binding(CounterScreenModel::class) { CounterScreen(it) },
        Binding(TicTacToeScreenModel::class) { TicTacToeScreen(it) }
      )
    )
  ) {
    TTTTheme {
      Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
      ) {
        LocalScreenRegistry.current.ContentFor(root)
      }
    }
  }
}
