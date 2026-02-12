package es.rafapuig.pmdm.quotesapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun QuoteListRoute(
    viewModel: QuoteListViewModel,
    onNavigateToQuoteDetails: (Int) -> Unit,
    onNavigateToAbout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is QuoteListEvent.OnNavigateToQuoteDetails -> onNavigateToQuoteDetails(event.quoteId)
                QuoteListEvent.OnNavigateToAbout -> onNavigateToAbout()
            }
        }
    }

    QuoteListScreen(
        quotes = uiState.quotes,
        onQuoteSelected = { quote -> viewModel.onAction(QuoteListAction.OnQuoteSelected(quote)) },
        onRandomQuote = { viewModel.onAction(QuoteListAction.OnRandomQuote) },
        onFavorite = { quote -> viewModel.onAction(QuoteListAction.OnToggleFavorite(quote)) },
        onFilter = { query -> uiState.filteredQuotes(query) },
        onAbout = { viewModel.onAction(QuoteListAction.OnAbout) } // 👈 aquí dispara la acción
    )
}
