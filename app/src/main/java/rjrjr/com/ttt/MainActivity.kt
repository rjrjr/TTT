package rjrjr.com.ttt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import rjrjr.com.ttt.counter.Counter
import rjrjr.com.ttt.counter.CounterUi
import rjrjr.com.ttt.framework.ProvideLocalUiBindings
import rjrjr.com.ttt.framework.ShowUi
import rjrjr.com.ttt.framework.UiBinding
import rjrjr.com.ttt.framework.UiModel
import rjrjr.com.ttt.poetry.STANZA_BINDINGS
import rjrjr.com.ttt.tictactoe.TicTacToe
import rjrjr.com.ttt.tictactoe.TicTacToeUi
import rjrjr.com.ttt.ui.theme.TTTTheme

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
      rootPresenter.present(Unit)
    }
  }
}

@Composable
fun AppUi(roots: StateFlow<UiModel>) {
  val root by roots.collectAsState()

  ProvideLocalUiBindings(
    listOf(
      UiBinding(CounterUi::class) { Counter(it) },
      UiBinding(TicTacToeUi::class) { TicTacToe(it) },
    ) + STANZA_BINDINGS
  ) {
    TTTTheme {
      Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
      ) {
        ShowUi(root)
      }
    }
  }
}
