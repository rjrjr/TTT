# What's all this then?

[I'm](https://nyc.droidcon.com/ray-ryan/) working on my droidcon NYC 2022 talk, _Navigation and Dependency Injection in Compose_, and this is the scratch project to check that my ideas actually, like, work.

tl;dr:

[Here's the interesting code](../../tree/main/app/src/main/java/rjrjr/com/ttt).

* A tree of `@Composable` presenter functions is run by a **non-UI** Compose runtime (courtesy of [Molecule](https://github.com/cashapp/molecule))
* They build a complete struct-like `UiModel`, itself a tree of smaller `UiModel` structs. 
* That is passed to a top level Compose UI  `@Composable` function, 
* which is able to map sub-view model structs to other `@Composables` via `UiContentRegistry.Content`.

So far it's about:

- A clean pattern for creating presenters (aka [workflows](https://square.github.io/workflow/glossary/#workflow-instance), if you're familiar with [Square Workflow](https://square.github.io/workflow/)) via DI -- Molelcule examples offer no guidance there, Presenter interface is my own

- Demonstrating including event handlers directly on model structs ([renderings](https://square.github.io/workflow/glossary/#rendering)) so that they're self contained -- [Molecule examples](https://github.com/cashapp/molecule/blob/0.4.0/sample/src/main/java/com/example/molecule/CounterActivity.kt#L41) and Cash team idiom is to instead use Event classes, and Molecule provides support for a [Flow based event collector](https://github.com/cashapp/molecule/blob/0.4.0/sample/src/main/java/com/example/molecule/view.kt#L22) to be managed by feature code and provided as an argument to each presenter function.

- Demonstrating how the self contained renderings allow generic containers to function. In Cash, every container is hand built, has to know the types of its subviews

The upside of this approach is that it's really, really simple. There are two costs:

- No event / action queue, so no time machine demo, no smart throttling of events, etc. That's a big departure from both workflow and the Cash idiom

- If a new presenter takes over a view structure built by a previous one, recomposition will happen. Cash imagines caring about that scenario some day

I think we should be able to have our cake (workflow style self contained view models) and eat it too (central event dispatch) without making feature developers pay too high a price. But I wonder if it would actually worth bothering, even in a large app. [Martin Fowler has opinions](https://martinfowler.com/eaaDev/EventSourcing.html#:~:text=Event%20Sourcing.-,When%20to%20Use%20It,-Packaging%20up%20every). Probably yes, so it's worth exploring how to get that. Dunno if time permits before the talk, though.

Next step, proper navigation.
