package testImplementationsCoroutines

import kotlinx.coroutines.*

class MultipleThreadsCoroutines(dispatcher : CoroutineDispatcher) {
    private val scope = CoroutineScope(dispatcher);

    fun launch(){
        scope.launch {
            println("hello ${Thread.currentThread().name} + ${Thread.currentThread().id}")

            delay(1000L);
            var result = async { 23*3 }
            println("after async..")
            println(" result ${result.await()}")
        }

        scope.launch{
            println("hello2 ${Thread.currentThread().name} + ${Thread.currentThread().id}")

            delay(11L);
            println("scope2")
            delay(10L)
            println("end2")
        }

        scope.launch{
            println("hello3 ${Thread.currentThread().name} + ${Thread.currentThread().id}")

            delay(4L);
            println("scope4")
            delay(15L)
            println("end")
        }
    }
}