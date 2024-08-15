package testImplementationsCoroutines.suspendCoroutines

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SuspendCoroutines {

    fun start() = runBlocking{
        val scope = CoroutineScope(Dispatchers.IO)
        launch {
            println("starting coroutine with suspension ${Thread.currentThread().name} with id ${Thread.currentThread().id}")

            myDelay(1000L)

            val a = 3+2;
            println("after suspension")
            println("a = $a")
        }

        launch {
        println("second coroutine without suspension ${Thread.currentThread().name} with id ${Thread.currentThread().id}")
        something()
        println("after something")
        }

    }

    private suspend fun something(){
      var result = 1;
        for (i in 10..99){
            result = result * i-i;
            result*=result;
        }
        println("result = ${result}")
    }

    private val executor =
        Executors.newSingleThreadScheduledExecutor {
            Thread(it, "scheduler").apply { isDaemon = true }
        }
    suspend fun myDelay(time: Long): Unit =
        suspendCoroutine { cont->
            executor.schedule({
                cont.resume(Unit)
            }, time, TimeUnit.MILLISECONDS)
        }
}