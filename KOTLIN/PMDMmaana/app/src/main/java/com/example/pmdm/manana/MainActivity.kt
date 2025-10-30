package com.example.pmdm.manana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pmdm.manana.ui.theme.PMDMmañanaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PMDMmañanaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Buen día $name!",
        modifier = modifier
    )
}
//@Composable
//fun PersonList(person: Person){
//    for(p in PersonList())
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
//    PMDMmañanaTheme {
//        Greeting("Brandon")
//    }
    PersonItemPreview();
//    Colum{
//        PersonList(persons)
//    }
}