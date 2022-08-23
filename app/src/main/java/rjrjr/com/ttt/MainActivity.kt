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
import rjrjr.com.ttt.UiContentRegistry.Binding
import rjrjr.com.ttt.presenter.CounterModel
import rjrjr.com.ttt.presenter.StanzaModel
import rjrjr.com.ttt.presenter.TicTacToeModel
import rjrjr.com.ttt.ui.theme.TTTTheme
import rjrjr.com.ttt.view.CounterView
import rjrjr.com.ttt.view.STANZA_BINDINGS
import rjrjr.com.ttt.view.TicTacToeView

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

  CompositionLocalProvider(
    LocalUiContentRegistry provides UiContentRegistry(
      listOf(
        Binding(CounterModel::class) { CounterView(it) },
        Binding(TicTacToeModel::class) { TicTacToeView(it) },
      ) + STANZA_BINDINGS
    )
  ) {
    TTTTheme {
      Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
      ) {
        LocalUiContentRegistry.current.ContentFor(root)
      }
    }
  }
}
