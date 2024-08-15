package testImplementationsCoroutines.suspendCoroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PersonSuspendCoroutineWithCancableTest {
    val service = PersonService()

        suspend fun test() = coroutineScope {
            val person = getPersons(StateForSuspend.FAILURE)
            println("name ${person.name} surname ${person.secondName} age ${person.age} ")
        }

        suspend fun getPersons(state: StateForSuspend) : Person {
        return suspendCancellableCoroutine<Person> { cont ->
            service.getPerson(state, {person -> cont.resume(person)}, {exp ->  cont.resumeWithException(Exception(exp))})
        }
    }
}