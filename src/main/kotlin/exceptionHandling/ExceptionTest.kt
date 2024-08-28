package exceptionHandling

import kotlinx.coroutines.*

class ExceptionTest {

    /**
     * Error thrown from child1 is propagated to parent and then to the main method. Than the fathers
     * cancel all the child and the program ends with exception
     */
   suspend fun testExceptionPropagation() = coroutineScope {
        var context = CoroutineName("ExceptionPropagationCoroutine") + Dispatchers.IO

        launch(context) {
            println("Coroutine: ${currentCoroutineContext()} ${Thread.currentThread().name} ${Thread.currentThread().id}")

            launch(CoroutineName("Child1")) {
                println("Coroutine: ${currentCoroutineContext()} ${Thread.currentThread().name} ${Thread.currentThread().id}")
                throw Error("My error to propagate")
            }

            launch(CoroutineName("Child2")) {
                println("Coroutine: ${currentCoroutineContext()} ${Thread.currentThread().name} ${Thread.currentThread().id}")

            }

        }


    }

    /**
     * CoroutineExceptionHandler can add handler for case where the exception is thrown. It can be added
     * to coroutine context like Dispatcher or CoroutineName
     */
    suspend fun testExceptionHandling() = coroutineScope{
        var handler =  CoroutineExceptionHandler {ctx, e -> println("Handler for the coroutine exception with exception: $e") }
        var context = CoroutineName("ExceptionHandlingCoroutine") + Dispatchers.IO + handler
        var scope = CoroutineScope(context)

        scope.launch{
            throw Error("myerrror")
        }

    }

    /**
     * to stop propagation is need to use supervisorJob or supervisorScope. Both of the cases stop
     * the propagation and program can still run. When using await with async, the exception will be thrown
     * when await is called.
     */
    suspend fun testStopExceptionPropagation() = coroutineScope{
        var context = CoroutineName("ExceptionHandlingCoroutine") + Dispatchers.IO
        var scope = CoroutineScope(context)
        var job = SupervisorJob()

       var job1 = scope.launch(job){
            println("start first scope - stopping with SupervisorJob")
            throw Error("Some error1")
        }

       var job2 = scope.launch(job){
            println("start second scope - stopping with SupervisorJob")
            throw Error("Some error2")
        }

        job1.join()
        job2.join()

        //program will continue because the propagation stopping works. Error will stop in the parent coroutine
        println("after first two scopes")
        println(".....")
        delay(2000L)
        println("start supervisorScope stopping")

        supervisorScope {
                var job3 = launch {
                    println("start third scope - stopping with supervisorScope")
                    throw Error("Some error 3")
                }

                var job4 = launch {
                    println("start fourth scope - stopping with supervisorScope")
                }

                job3.join()
                job4.join()
                delay(2000L)
                println("End of the supervisorScope stopping")
            }

    }
}