package rjrjr.com.ttt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

// Compare with the Molecule sample, which passes a Flow<Event> as
// an arg to the presenter function, instead of these lambdas. Note that we're abandoning all
// the benefits of a shared event stream -- aka the action sink in workflow
// Does that matter, if you don't particularly want the time machine demo? It comes with the
// huge downside of making nested presenters much more complicated.
// https://androidstudygroup.slack.com/archives/C04QK68FW/p1660162198681359

data class CounterModel(
  val value: Int,
  val loading: Boolean,
  val onChange: (delta: Int) -> Unit,
  val onRandomize: () -> Unit
)

@Composable
fun counterPresenter(
  randomService: RandomService,
): CounterModel {
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
