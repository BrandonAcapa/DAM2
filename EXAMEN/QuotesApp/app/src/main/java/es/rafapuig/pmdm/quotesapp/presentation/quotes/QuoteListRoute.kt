package es.rafapuig.pmdm.quotesapp.presentation.quotes

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import es.rafapuig.pmdm.quotesapp.presentation.ObserveAsEvents
import es.rafapuig.pmdm.quotesapp.presentation.screens.quotelist.QuoteListScreen
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun QuoteListRoute(
    viewModel: QuoteListViewModel,
    onNavigateToQuoteDetails: (Int) -> Unit = {},
    onNavigateToAbout: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    // Observe one-shot events
    viewModel.events.ObserveAsEvents { event ->
        when (event) {
            is OnNavigateToQuoteDetails -> onNavigateToQuoteDetails(event.quoteId)
        }
    }

    val onAction: (QuoteListAction) -> Unit = { action ->
        when (action) {
            is QuoteListAction.OnAbout -> onNavigateToAbout()
            is QuoteListAction.OnQuoteSelected -> onNavigateToQuoteDetails(action.quote.id)
            else -> viewModel.onAction(action)
        }
    }

    QuoteListScreen(
        quotes = uiState.value.quotes,
        onQuoteSelected = { quote -> onAction(QuoteListAction.OnQuoteSelected(quote)) },
        onRandomQuote = { onAction(QuoteListAction.OnRandomQuote) },
        onFavorite = { id -> viewModel.onAction(QuoteListAction.OnToggleFavorite(id)) },
        onFilter = { _ -> uiState.value.quotes },
        onAbout = { onAction(QuoteListAction.OnAbout) },
        onShare = {}
    )
}
