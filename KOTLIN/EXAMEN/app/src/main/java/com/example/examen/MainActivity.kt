package com.example.examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.examen.ui.theme.ExamenTheme
import com.example.examen.pantallas.WelcomeScreen
import com.example.examen.pantallas.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                        val navController = rememberNavController()

                        NavHost(navController = navController, startDestination = "main") {
                            composable(route = "main") {
                                MainScreen(onNavigate = {
                                    nombre -> navController.navigate("welcome/$nombre")
                                })
                            }
                            composable(
                                route = "welcome/{name}",
                                arguments = listOf(navArgument("name") {
                                    type = NavType.StringType}))
                            {
                                    backStackEntry ->
                                val name = backStackEntry.arguments?.getString("name")
                                WelcomeScreen(name = name ?: "Invitado")
                            }
                        }
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ExamenTheme {
//        Greeting("Android")
//    }
//}