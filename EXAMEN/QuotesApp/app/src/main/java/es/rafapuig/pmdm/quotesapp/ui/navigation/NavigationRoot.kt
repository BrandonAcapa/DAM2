package es.rafapuig.pmdm.quotesapp.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import es.rafapuig.pmdm.quotesapp.presentation.quotes.QuoteListRoute
import es.rafapuig.pmdm.quotesapp.presentation.details.QuoteDetailsRoute
import es.rafapuig.pmdm.quotesapp.presentation.screens.about.AboutScreen
import kotlinx.serialization.Serializable


@Serializable
data object QuoteListScreenKey : NavKey

@Serializable
data class QuoteDetailsScreenKey(val quoteId: Int) : NavKey

@Serializable
data object AboutScreenKey : NavKey


val LocalOnNavigationBack = staticCompositionLocalOf<() -> Unit> { {} }


@Composable
fun NavigationRoot() {

    val backStack = rememberNavBackStack(QuoteListScreenKey)

    BackHandler(enabled = true) {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    CompositionLocalProvider(LocalOnNavigationBack provides { backStack.removeLastOrNull() }) {

        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider {

                entry<QuoteListScreenKey> {
                    val vm: es.rafapuig.pmdm.quotesapp.presentation.quotes.QuoteListViewModel = koinViewModel()
                    QuoteListRoute(
                        viewModel = vm,
                        onNavigateToQuoteDetails = { id -> backStack.add(QuoteDetailsScreenKey(id)) },
                        onNavigateToAbout = { backStack.add(AboutScreenKey) }
                    )
                }

                entry<QuoteDetailsScreenKey> { key ->
                    val vm: es.rafapuig.pmdm.quotesapp.presentation.details.QuoteDetailsViewModel =
                        koinViewModel(parameters = { parametersOf(key.quoteId) })
                    QuoteDetailsRoute(
                        viewModel = vm,
                        onBack = { backStack.removeLastOrNull() }
                    )
                }

                entry<AboutScreenKey> {
                    AboutScreen(
                        onBack = { backStack.removeLastOrNull() }
                    )
                }

            }
        )
    }

}


