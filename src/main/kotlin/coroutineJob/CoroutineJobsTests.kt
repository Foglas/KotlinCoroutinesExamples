package coroutineJob

import kotlinx.coroutines.*

class CoroutineJobsTests {

    /**
     * test waiting for coroutine. In case where is need to wait for some action will be completed,
     * Join() can wait for the job. Job is only thing which is created new for every coroutine and it
     * describes for example the state of coroutine or on the job we can perform some actions like waiting,
     * or cancelling
     */
    suspend fun testJoin() = coroutineScope{
        val context = Dispatchers.IO + CoroutineName("jobTest")

        launch(context){
           println("Coroutine:  ${currentCoroutineContext()}  thread: ${Thread.currentThread().name} ${Thread.currentThread().id}" )
            delay(2000L)
            println("Coroutine1 completed")
        }


      val job =  launch(context.plus(CoroutineName("jobTest2"))){
            println("Coroutine2:  ${currentCoroutineContext()}  thread: ${Thread.currentThread().name} ${Thread.currentThread().id}" )
            delay(3000L)
            println("Coroutine2 completed")

        }

      //wait for the all coroutine are completed
      job.join()
      println("method ends")
    }

    /**
     * Coroutine have to have suspension point, on which the coroutine can be canceled. When the
     * coroutine don´t have any suspension point, the coroutine can´t be canceled. So we need to add some
     * suspension point
     */
    suspend fun testCancellation() = coroutineScope{
        val context = Dispatchers.IO + CoroutineName("cancellationTest")

       val job = launch(context){
           try {
               println("Coroutine: ${currentCoroutineContext()} thread: ${Thread.currentThread().name} ${Thread.currentThread().id}")
               delay(2000L)
               println("middle of coroutine")
               delay(1000L)

           } catch (e: CancellationException){
               println("Coroutine was canceled")

           } finally {
               println("will be printed every time")
           }
           }

        //called on cancellation too
        job.invokeOnCompletion { println("coroutine was completed") }

        delay(1900L)
        job.cancelAndJoin();
    }

    /**
     * shows the firstOption how to do suspension point with ensureActive, every time is in the
     * coroutine scope
     */
    suspend fun cancelWithSuspensionPointWithEnsureActive(isSuspensionPoint: Boolean) = coroutineScope{
        val context = Dispatchers.IO + CoroutineName("cancellationTestWithSuspensionPoint")

       var job = launch(context){
            repeat(400) { x ->
                println("x: ${x}")
                if (isSuspensionPoint) {
                    ensureActive()
                }
            }
        }

        job.cancel()

        println("canceled")


    }

    /**
     * Suspension point where is used yield, most use cases is in the suspend functions with CPU
     * intensive work
     */
    suspend fun cancelWithSuspensionPointWithYield(isSuspensionPoint: Boolean) = coroutineScope{
        val context = Dispatchers.IO + CoroutineName("cancellationTestWithSuspensionPoint")

        var job = launch(context){
            repeat(400) { x ->
                println("x: ${x}")
                if (isSuspensionPoint) {
                    yield()
                }
            }
        }

        job.cancel()

        println("canceled")


    }

    /**
     * Suspension point by using checking if the coroutine is active
     */
    suspend fun cancelWithSuspensionPointWithIsActiveChecking(isSuspensionPoint: Boolean) = coroutineScope{
        val context = Dispatchers.IO + CoroutineName("cancellationTestWithSuspensionPoint")

        var job = launch(context){
            if (isSuspensionPoint) {
                var i = 0
                do {
                    i++
                    println("x ${i}")
                } while (isActive)
            } else {
                repeat(400) { x ->
                    println("x ${x}")
                }
            }
        }

        job.cancel()

        println("canceled")


    }

    /**
     * withContext(NonCancellable) support the suspending function. So by default in the finally
     * block is not supported to run suspending function, however with using of withContext the
     * suspending function like delay can be used
     */
    suspend fun cancelWithSuspensionInFinallyBlock() = coroutineScope{
        val context = Dispatchers.IO + CoroutineName("cancellationWithSuspensionInCatchBlock")

        var job = launch(context){
            try {
                repeat(300){ num ->
                    println("x: ${num}")
                    ensureActive()
                }
            } catch (e: CancellationException){
                println("catched cancellation exception")
            } finally {
                println("suspending not supported")

                withContext(NonCancellable){
                    println("support suspending block")
                    delay(2000L)
                    println("end of suspending block")
                }
            }
        }

        job.cancelAndJoin()
        println("end of test")

    }
}