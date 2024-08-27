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
           }
           }

        //called on cancellation too
        job.invokeOnCompletion { println("coroutine was completed") }

        delay(1900L)
        job.cancelAndJoin();


    }
}