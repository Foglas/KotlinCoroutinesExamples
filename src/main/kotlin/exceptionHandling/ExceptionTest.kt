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

    suspend fun testExceptionHandling() = coroutineScope{
        var handler =  CoroutineExceptionHandler {ctx, e -> println("Handler for the coroutine exception with exception: $e") }
        var context = CoroutineName("ExceptionHandlingCoroutine") + Dispatchers.IO + handler
        var scope = CoroutineScope(context)

        scope.launch{
            throw Error("myerrror")
        }
    }


}