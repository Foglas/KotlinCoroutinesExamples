package testImplementationsCoroutines

import kotlinx.coroutines.*

class CoroutinesRelationTest {

    suspend fun test() = coroutineScope{
    val scope = CoroutineScope(Dispatchers.IO)

        scope.launch {
            println("Actual thread in parent coroutine1: ${Thread.currentThread().name} id ${Thread.currentThread().id}")
            newCoroutine();
        }

        launch {
            println("Actual thread in parent coroutine2: ${Thread.currentThread().name} id ${Thread.currentThread().id}")

            newCoroutine();
        }

        launch {
            println("Actual thread in parent coroutine3: ${Thread.currentThread().name} id ${Thread.currentThread().id}")
            newCoroutineWithDelay(2100L)
        }
    }

    suspend fun newCoroutine() = coroutineScope {
        println("Actual thread in child coroutine: ${Thread.currentThread().name} id ${Thread.currentThread().id}")
    }

    suspend fun newCoroutineWithDelay(time: Long) = coroutineScope {
        delay(time)
        println("Actual thread in child with delay coroutine: ${Thread.currentThread().name} id ${Thread.currentThread().id}")

    }
}