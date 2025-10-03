package funtional;

fun double(x: Int) = x * 2;

val triple = fun(x: Int) = x * 3;
val square = fun(x: Int) = x * x;

val half = {x: Int -> x / 2;}
val twoMore: (Int) -> Int = {x -> x + 2}
val oneLess: (Int) -> Int = {it - 1}
val length: (String) -> Int = {it.length}

fun operateWith(n:Int, operation: (Int) -> Int): Int {
    return operation(n);
};

fun Int.operateWith(operation: (Int) -> Int): Int {
    return operation(this);
};

fun List<Int>.count(predicate: (Int)->Boolean) : List<Int> {
    val pares = mutableListOf<Int>()

    forEach { if (predicate(it)) pares += it }

    return pares.toList()
}

//fun countPares(numbers: List<Int>) {
//    val pares = mutableListOf<Int>();
//    for (number in numbers) {
//        if (number % 2 == 0) {
//            pares.add(number);
//        }
//    }
//    return pares.toList();
//}

fun main() {
//    val result4 = operateWith(7, square);
//    val result5 = operateWith(7, ::double);  // :: convierte una función en una variable
//
//    var operations = arrayOf(::double, triple, square);
//
//    for (operation in operations) {
//        println(operation(5));
//    }

    val result = 5.operateWith (oneLess );
//    val result2 = 50.operateWith ( { it / 10 } );
//    val result3 = 50.operateWith (){ it / 10 };
    val result4 = 50.operateWith { it / 10 };

    val numbers = listOf(1, 2, 3, 4, 5)
    val pares = numbers.count { it % 2 == 0 }
    val imparesList = numbers.filter { it % 2 == 1 }
    val max = numbers.filter { it % 2 == 0 }.max()

}

