package coroutineContext

import kotlinx.coroutines.*

class CoroutineContextTest {


    //coroutines inherit whole context except Job, which is created new for every coroutine.
    suspend fun createCoroutine() = coroutineScope{
        println("Thread in coroutineContext: ${Thread.currentThread().name}  id ${Thread.currentThread().id} ${currentCoroutineContext()[CoroutineName]} and whole info: ${currentCoroutineContext()}")

        //creating new context, these attributes will replace the old one in the inherited context. That is displayed on print
        val newCotext = Dispatchers.IO + CoroutineName("child")
        launch(newCotext) {
            println("Thread in coroutineContext first launch: ${Thread.currentThread().name}  id ${Thread.currentThread().id} with coroutine name  ${currentCoroutineContext()[CoroutineName]} and whole info: ${currentCoroutineContext()}")

        }
    }
}