package rjrjr.com.ttt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.AndroidUiDispatcher.Companion.Main
import app.cash.molecule.RecompositionClock
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.plus
import rjrjr.com.ttt.ui.theme.TTTTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val continuity: Continuity by viewModels()
    val models = continuity.models

    setContent {
      TTTTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Root(models)
        }
      }
    }
  }

  class Continuity : ViewModel() {
    private val scope = viewModelScope + Main
    val models = scope.launchMolecule(clock = RecompositionClock.ContextClock) {
      RootPresenter().present()
    }
  }
}

class RootPresenter : Presenter<RootModel> {
  // Should RootPresenter be hoisted to a higher Composable that invokes Dagger
  // to create it? Would put Compose in charge of _all_ state.

  private val randomService = RandomService()
  private val counterPresenter = CounterPresenter(randomService)

  @Composable override fun present(): RootModel = Pair(
    counterPresenter.present(),
    TicTacToePresenter.present()
  )
}
