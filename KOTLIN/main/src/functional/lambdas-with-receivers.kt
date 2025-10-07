package functional

fun alphabet(): String {
    var result = ""
    for (c in 'A'..'Z'){
        result += c
    }
    result = "$result\nYa se el alfabeto!!"
    return result
}

fun main() {
    println(alphabet())

//  .with
//  .apply
}