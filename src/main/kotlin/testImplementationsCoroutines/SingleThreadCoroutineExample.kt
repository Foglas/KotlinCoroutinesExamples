package testImplementationsCoroutines

import kotlinx.coroutines.*

class SingleThreadCoroutineExample {
    companion object{

        fun launchWIthSingleThead() = runBlocking{
            val singleThreadContext = newSingleThreadContext("SingleThread")

            launch(singleThreadContext) {
                println("hello ${Thread.currentThread().name} + ${Thread.currentThread().id}")

                delay(1000L);
                var result = async { 23*3 }
                println("after async..")
                println(" result ${result.await()}")
            }

            launch(singleThreadContext){
                println("hello2 ${Thread.currentThread().name} + ${Thread.currentThread().id}")

                delay(11L);
                println("scope2")
                delay(10L)
                println("end2")
            }

            launch(singleThreadContext){
                println("hello3 ${Thread.currentThread().name} + ${Thread.currentThread().id}")

                delay(4L);
                println("scope4")
                delay(15L)
                println("end")
            }
        }

        fun launchInMainThread() = runBlocking {
            launch {
                println("hello ${Thread.currentThread().name} + ${Thread.currentThread().id}")

                delay(1000L);
                var result = async { 23*3 }
                println("after async..")
                println(" result ${result.await()}")
            }

            launch{
                println("hello2 ${Thread.currentThread().name} + ${Thread.currentThread().id}")

                delay(11L);
                println("scope2")
                delay(10L)
                println("end2")
            }

            launch{
                println("hello3 ${Thread.currentThread().name} + ${Thread.currentThread().id}")

                delay(4L);
                println("scope4")
                delay(15L)
                println("end")
            }


        }
    }
}