package testImplementationsCoroutines

import kotlinx.coroutines.*

class CoroutinesRelationTest {

    suspend fun test() = coroutineScope{
    val scope = CoroutineScope(Dispatchers.IO)


        /*launch new coroutine on new Thread (if the new thread is available) and then the method newCoroutine inherit the context of parent.
         That means the prints show same thread with same id and if there is used delay, the delay stopped the parent too.
        * */
        scope.launch {
            println("Actual thread in parent coroutine1: ${Thread.currentThread().name} id ${Thread.currentThread().id}")
            newCoroutine("in new IO scope");
        }



        /*Next two launches start new coroutines on the same thread, but with different context. When invoking newCoroutineWithDelay, the
         coroutine is suspended and the second can be performed. Thats because there is two coroutines with different contexts.
         */
        launch {
            println("Actual thread in parent coroutine2: ${Thread.currentThread().name} id ${Thread.currentThread().id}")

            newCoroutineWithDelay(2100L,"of the second main coroutine")

        }

        launch {
            println("Actual thread in parent coroutine3: ${Thread.currentThread().name} id ${Thread.currentThread().id}")
            newCoroutine("of the third main coroutine");

        }
    }


    suspend fun newCoroutine(message: String) = coroutineScope {
        println("Actual thread in child $message coroutine: ${Thread.currentThread().name} id ${Thread.currentThread().id}")
    }

    suspend fun newCoroutineWithDelay(time: Long, message: String) = coroutineScope {
        delay(time)
        println("Actual thread in child $message with delay coroutine: ${Thread.currentThread().name} id ${Thread.currentThread().id}")

    }
}