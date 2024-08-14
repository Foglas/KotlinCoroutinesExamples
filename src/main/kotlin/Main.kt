import kotlinx.coroutines.*
import sequences.FaktorialExamples
import testImplementationsCoroutines.MultipleThreadsCoroutines
import testImplementationsCoroutines.SuspendCoroutines

fun main(args: Array<String>) = runBlocking {


    //MultipleThreadsCoroutines(Dispatchers.IO).launch();

    //println("continuing");

    //FaktorialExamples.work();

    SuspendCoroutines().start();
    delay(2000L)
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}