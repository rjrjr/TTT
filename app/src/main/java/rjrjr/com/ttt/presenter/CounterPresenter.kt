package rjrjr.com.ttt.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import rjrjr.com.ttt.Presenter
import rjrjr.com.ttt.UiModel
import rjrjr.com.ttt.data.RandomService

/**
 * Compare with the
 * [Molecule sample](https://github.com/cashapp/molecule/blob/0.4.0/sample/src/main/java/com/example/molecule/presenter.kt#L32),
 * which passes a `Flow<Event>` as an arg to the presenter function,
 * instead of hanging event handler lambdas off the model. Note that we're abandoning all the
 * benefits of a shared event stream -- `ActionSink` in workflow, `callbackFlow` in Molecule .
 *
 * Cost:
 *  - No time machine demo
 *  - New presenter can't take over management of an existing view structure, recomposition
 *    will be forced
 *  - Lose all the benefits of a stream of events, which is damn powerful
 *
 * Benefit:
 *  - So very, very simple
 *
 * If we care about the costs, should be able to have our cake (self contained models)
 * and eat it too (single global, event stream that can be throttled, replayed), but
 * presenters would have to get more complicated -- inject the event stream, distinguish
 * their events from everyone else's.
 *
 * That gets tricky as soon as there are multiple instances of the same type of
 * presenter / screen in play, can't just filter on event type. Perhaps can use
 * CompositionLocal to create presenter namespace with minimum of fuss. Probably
 * out of scope for this preso beyond Extra for Experts slide.
 *
 * https://androidstudygroup.slack.com/archives/C04QK68FW/p1660162198681359
 */
data class CounterModel(
  val value: Int,
  val loading: Boolean,
  val onChange: (delta: Int) -> Unit,
  val onRandomize: () -> Unit
) : UiModel

class CounterPresenter(
  private val randomService: RandomService,
) : Presenter<Unit, CounterModel> {
  @Composable override fun present(props: Unit): CounterModel {
    var count by remember { mutableStateOf(0) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    return CounterModel(
      count,
      loading,
      onChange = { count += it },
      onRandomize = {
        loading = true
        scope.launch {
          count = randomService.get(-20, 20)
          loading = false
        }
      }
    )
  }
}
