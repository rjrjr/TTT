package rjrjr.com.ttt.poetry

import kotlinx.coroutines.delay

interface PoemService {
  suspend fun getPoem(id: Int): Poem

  suspend fun getPoemIndex(): Map<Int, String>

  companion object AllPoems : PoemService {
    private val allPoems: List<Poem> = listOf(
      TheConquerorWorm,
      ToHelen,
      Raven,
      TheTyger
    )

    override suspend fun getPoem(id: Int): Poem {
      delay(1000)
      return allPoems[id]
    }

    override suspend fun getPoemIndex(): Map<Int, String> {
      delay(1000)
      return allPoems.withIndex().associate { (it.index to it.value.title) }
    }
  }
}
