package sequences

import kotlinx.coroutines.*
import java.math.BigInteger

class Factorial {

    companion object{
     suspend fun calculate(number: Int): BigInteger{

         val scope = CoroutineScope(Dispatchers.IO);
         var result: BigInteger = 0.toBigInteger();


       val job =  scope.launch {

           val seq: Sequence<BigInteger> = sequence{
             var actualNumber : BigInteger = 1.toBigInteger();
             var actualFactorial : BigInteger = 1.toBigInteger();

             while (true){
                 actualFactorial *= actualNumber++
                 yield(actualFactorial)
           }
         }
             result = seq.take(number).last();
        }
         job.join()

         return result;
     }

     suspend fun calculateList(number: Int): List<Int>{
         val scope = CoroutineScope(Dispatchers.IO);
         var result: List<Int> = ArrayList();


         val job =  scope.launch {

             val seq: Sequence<Int> = sequence{
                 var actualNumber : Int = 1;
                 var actualFactorial : Int = 1;

                 while (true){
                     actualFactorial *= actualNumber
                     yield(actualFactorial)
                     actualNumber++
                 }
             }
             result = seq.take(number).toList();
         }
         job.join()

         return result;
     }
    }
}