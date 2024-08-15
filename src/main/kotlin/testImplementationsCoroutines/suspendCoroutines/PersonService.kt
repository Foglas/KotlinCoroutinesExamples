package testImplementationsCoroutines.suspendCoroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.ConcurrentModificationException
import kotlin.coroutines.Continuation

class PersonService {

     fun getPerson(state: StateForSuspend, onSucces: (person: Person)-> Unit, onError: (exp: String)->Unit) {
         val person = Person("Adam", "Borek",24)
         when(state){
             StateForSuspend.SUCCESSFUL -> {onSucces(person)}
             StateForSuspend.FAILURE -> {val exp = "wrong person";onError(exp)}
         }
    }
}