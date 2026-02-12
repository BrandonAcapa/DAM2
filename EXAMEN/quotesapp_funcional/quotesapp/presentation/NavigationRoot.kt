package es.rafapuig.pmdm.quotesapp.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import es.rafapuig.pmdm.quotesapp.data.QuoteProvider
import es.rafapuig.pmdm.quotesapp.domain.QuoteManager
import es.rafapuig.pmdm.quotesapp.presentation.screens.AboutScreen
import es.rafapuig.pmdm.quotesapp.presentation.screens.QuoteDetailsScreen
import es.rafapuig.pmdm.quotesapp.presentation.screens.QuoteListScreen
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

    val onFavorite = { quoteId: Int ->
        QuoteManager.toggleFavorite(quoteId)
    }

    BackHandler(enabled = true) {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    CompositionLocalProvider(LocalOnNavigationBack provides { backStack.removeLastOrNull() }) {

        NavDisplay(
            backStack = backStack,
            //onBack = { if (backStack.size > 1) backStack.removeLastOrNull() },
            entryProvider = entryProvider {

                entry<QuoteListScreenKey> {
                    QuoteListScreen(
                        quotes = QuoteManager.quotes,
                        onQuoteSelected = { quote ->
                            backStack.add(QuoteDetailsScreenKey(quote.id))
                        },
                        onRandomQuote = {
                            backStack.add(QuoteDetailsScreenKey(QuoteProvider.randomQuote().id))
                        },
                        onFilter = { query -> QuoteManager.filteredQuotes(query) },
                        onFavorite = onFavorite,
                        onAbout = { backStack.add(AboutScreenKey) }
                    )
                }

                entry<QuoteDetailsScreenKey> { key ->
                    QuoteDetailsScreen(
                        QuoteManager.getQuoteById(key.quoteId),
                        onFavorite = onFavorite
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


