@JvmInline
value class Surface(val value: Int) {
    override fun toString(): String {
        return value.toString() + "m2"
    }
}

val Int.km2 get() = Surface(this * 1000 * 1000)

//fun main() {
//    val surface1 = 5.km2
//    println(surface1) // Output: 5000000m2
//}

//val articles = setOf("el", "la", "los", "las");
val names = listOf("rafa", "Luis", "rafa", "Luis")
val articles = mutableSetOf("el", "la", "los", "las");

val l1 = listOf(5, 8)
val l2 = listOf(3, 4)

//val rankMap = mapOf(
//    Pair(1, "Oro"),
//    Pair(2, "Plata"),
//    Pair(3, "Bronce")
//)

//val rankMap = mutableMapOf(
//    1 to "Oro",
//    2 to "Plata",
//    3 to "Bronce"
//)

val fruits = listOf("manzana", "pera", "uva", "melon")

fun main() {
//    articles.add("un")
//    val mutablesNames = names.toMutableList();
//    mutablesNames.add("pedro")
//
//    val l3 = l1 +

//    rankMap[4] = "Chocolate"
//
//    val primerPremio = rankMap[1]
//
//    rankMap.values.forEach { println(it) }

    val result = mutableListOf<Int>()
    for (fruit in fruits) {
        if (fruit.startsWith("m")) {
            result += fruit.length
        }
        println(result)
    }

    val result2 = fruits.map { it.length }
    println(result2)
}