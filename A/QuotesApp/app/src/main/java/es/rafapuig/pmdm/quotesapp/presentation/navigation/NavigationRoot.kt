package es.rafapuig.pmdm.quotesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.rafapuig.pmdm.quotesapp.presentation.screens.list.QuoteListRoute
import es.rafapuig.pmdm.quotesapp.presentation.screens.details.QuoteDetailsRoute
import es.rafapuig.pmdm.quotesapp.presentation.screens.about.AboutScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavigationRoot(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "quote_list" // O la NavKey que estés usando para la lista
    ) {
        // 1. Pantalla de Lista
        composable("quote_list") {
            QuoteListRoute(
                viewModel = koinViewModel(), // Inyección automática por Koin
                onNavigateToQuoteDetails = { quoteId ->
                    navController.navigate("quote_details/$quoteId")
                },
                onNavigateToAbout = {
                    navController.navigate("about")
                }
            )
        }

        // 2. Pantalla de Detalle (con paso de ID)
        composable("quote_details/{quoteId}") { backStackEntry ->
            val quoteId = backStackEntry.arguments?.getString("quoteId")?.toIntOrNull() ?: 0

            QuoteDetailsRoute(
                // Pasamos el ID al ViewModel a través de los parámetros de Koin
                viewModel = koinViewModel { parametersOf(quoteId) },
                quoteId = quoteId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // 3. Pantalla de About (Suele ser una Screen simple sin ViewModel complejo)
        composable("about") {
            AboutScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}