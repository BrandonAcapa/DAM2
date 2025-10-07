package exercises

import model.geography.Continent
import model.geography.Countries

fun printEuropeanCountries() {
    val result = Countries.WORLD_COUNTRIES
        .filter { it.continent == Continent.EUROPE }

    result.forEach { println(it) }
}

fun printAllAmericanCountriesSortedByName() {
    val result = Countries.WORLD_COUNTRIES
        .filter { it.continent == Continent.AMERICAS }
//        .sortedBy { it.name }

    result.forEach { println(it) }
}

fun example() {
    val result = Countries.WORLD_COUNTRIES
        .groupBy { it.continent }
        .mapValues { it.value.map { it.name } }

    result.forEach { println(it) }
}

fun mostPopulated() {
    val result = Countries.WORLD_COUNTRIES
       .filterNot{it.population == null }
        .minByOrNull { it.population!! }

    println("The most populated country is: ${result?.name}")
}

fun main() {
//    Countries.WORLD_COUNTRIES.forEach {
//        println(it)
//    }

//    printEuropeanCountries()

    printAllAmericanCountriesSortedByName()
}