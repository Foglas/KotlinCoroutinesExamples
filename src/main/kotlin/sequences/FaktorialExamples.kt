package sequences

import kotlinx.coroutines.runBlocking

class FaktorialExamples {
    companion object{
        fun work() = runBlocking{

            var result = Factorial.calculate(10);
            println("Result is: $result")

             var listResult = Factorial.calculateList(10);
             println("Result list is: $listResult")

             var newListResult = listResult.map { it/2 }.filter { it in 501..21000 }
             println("Result list2 is: $newListResult")

        }
    }
}