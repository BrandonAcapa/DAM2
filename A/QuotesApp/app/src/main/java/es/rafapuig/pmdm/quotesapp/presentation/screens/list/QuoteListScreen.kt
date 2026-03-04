package es.rafapuig.pmdm.quotesapp.presentation.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import es.rafapuig.pmdm.quotesapp.data.QuoteProvider
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.presentation.ObserveAsEvents
import es.rafapuig.pmdm.quotesapp.presentation.screens.list.components.QuoteListItem
import es.rafapuig.pmdm.quotesapp.presentation.screens.list.components.QuotesTopAppBar
import es.rafapuig.pmdm.quotesapp.presentation.screens.list.components.SearchBar
import es.rafapuig.pmdm.quotesapp.ui.theme.QuotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteListScreen(
    state: QuoteListUiState, // Recibe el estado completo del ViewModel
    onAction: (QuoteListAction) -> Unit // Canal único para todas las acciones
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            QuotesTopAppBar(
                scrollBehavior = scrollBehavior,
                // Delegamos cada interacción a una acción específica
                onRandom = { onAction(QuoteListAction.OnRandomQuote) },
                onSearch = { onAction(QuoteListAction.OnToggleSearchBar) },
                toggleFavorite = state.showFilterFavorites, // Usamos el valor del estado
                onFavorite = { onAction(QuoteListAction.OnToggleFilterFavorites) },
                onAbout = { onAction(QuoteListAction.OnAbout) }
            )
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            if (state.showSearchBar) {
                item {
                    SearchBar(
                        query = state.query,
                        onQueryChange = { onAction(QuoteListAction.OnQueryChange(it)) } //
                    )
                }
            }

            items(state.quotes) { quote ->
                QuoteListItem(
                    quote = quote,
                    onQuoteClick = { onAction(QuoteListAction.OnQuoteSelected(quote.id)) }, //
                    onFavoriteClick = { onAction(QuoteListAction.OnToggleFavorite(quote.id)) } //
                )
            }
        }
    }
}

class QuoteListProvider : PreviewParameterProvider<List<Quote>> {
    override val values: Sequence<List<Quote>>
        get() = sequenceOf(QuoteProvider.programmingQuotes)
}


@Preview(showSystemUi = true)
@Composable
fun QuoteListScreenPreview() {
    QuotesTheme {
        QuoteListScreen(
            state = QuoteListUiState(quotes = QuoteProvider.programmingQuotes),
            onAction = {}
        )
    }
}

@Composable
fun QuoteListRoute(
    viewModel: QuoteListViewModel,
    onNavigateToQuoteDetails: (Int) -> Unit,
    onNavigateToAbout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.events.ObserveAsEvents { event ->
        when (event) {
            is QuoteListEvent.OnNavigateToQuoteDetails -> onNavigateToQuoteDetails(event.quoteId)
            is QuoteListEvent.OnNavigateToAbout -> onNavigateToAbout()
        }
    }

    val onAction: (QuoteListAction) -> Unit = { action ->
        when (action) {
            is QuoteListAction.OnAbout -> onNavigateToAbout()
            is QuoteListAction.OnQuoteSelected -> onNavigateToQuoteDetails(action.quoteId)
            else -> viewModel.onAction(action)
        }
    }

    QuoteListScreen(
        state = uiState,
        onAction = onAction
    )
}