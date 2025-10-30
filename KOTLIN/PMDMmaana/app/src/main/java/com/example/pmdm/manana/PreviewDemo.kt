package com.example.pmdm.manana

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

data class Person(val name: String, val age: Int)

val persons = listOf(
    Person("Adan", 29),
    Person("Eva", 31),
    Person("Fran", 26)
)

@Composable
fun PersonItem(person: Person){
    Text("${person.name} is ${person.age} years old")
}

//@Composable
//fun PersonItem(person: Person){
//    Row{
//        Text(person.name)
//        Spacer(modifier = Modifier.width(8.))
//        Text(person.age.toString())
//    }
//}

@Preview(showBackground = true)
@Composable
fun PersonItemPreview(){
    PersonItem(Person("Rafa", 29))
}