import coroutineJob.CoroutineJobsTests
import kotlinx.coroutines.*


val name = CoroutineName("Father")
    val dispatcher = Dispatchers.Default;
    val defaultContext = dispatcher + name;

    suspend fun main(args: Array<String>) = withContext(defaultContext) {
    coroutineScope {
    println("Thread in main: ${Thread.currentThread().name}  id ${Thread.currentThread().id} and coroutine name ${coroutineContext[name.key]}")

    //MultipleThreadsCoroutines(Dispatchers.IO).launch();
    //println("continuing");
    //FaktorialExamples.work();
    //  val personCancelableCoroutine = PersonSuspendCoroutineWithCancableTest();
   // personCancelableCoroutine.test()
    // SuspendCoroutines().start();
    // var coroutinesRelationTest = CoroutinesRelationTest();
    //coroutinesRelationTest.test()

   // val coroutineContextTest = CoroutineContextTest()
   // coroutineContextTest.createCoroutine()

       val coroutineJobTests = CoroutineJobsTests()
        coroutineJobTests.testCancellation()
    delay(2000L)

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
}
}


