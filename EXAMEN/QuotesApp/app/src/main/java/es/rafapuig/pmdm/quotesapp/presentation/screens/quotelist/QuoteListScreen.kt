package es.rafapuig.pmdm.quotesapp.presentation.screens.quotelist

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
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.presentation.components.QuoteListItem
import es.rafapuig.pmdm.quotesapp.presentation.components.QuotesTopAppBar
import es.rafapuig.pmdm.quotesapp.presentation.components.SearchBar
import es.rafapuig.pmdm.quotesapp.ui.theme.QuotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteListScreen(
    quotes: List<Quote>,
    onQuoteSelected: (Quote) -> Unit = {},
    onRandomQuote: () -> Unit = {},
    onFavorite: (Int) -> Unit = {},
    onFilter: (String) -> List<Quote> = { quotes },
    onAbout: () -> Unit = {},
    onShare: (Quote) -> Unit = {}
) {
    var query by rememberSaveable { mutableStateOf("") }

    val filteredQuotes = onFilter(query)

    val scrollBehavior = TopAppBarDefaults
        .enterAlwaysScrollBehavior()

    var toggleSearchBar by rememberSaveable { mutableStateOf(false) }

    var toggleFilterFavorites by rememberSaveable { mutableStateOf(false) }


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            QuotesTopAppBar(
                scrollBehavior = scrollBehavior,
                onRandom = onRandomQuote,
                onSearch = { toggleSearchBar = !toggleSearchBar },
                toggleFavorite = toggleFilterFavorites,
                onFavorite = { toggleFilterFavorites = !toggleFilterFavorites },
                onAbout = onAbout
            )
        }
    ) {
        LazyColumn(
            contentPadding = it
        ) {
            if (toggleSearchBar) item {
                SearchBar(
                    query = query,
                    onQueryChange = { newQuery ->
                        query = newQuery
                    }
                )
            }

            items(filteredQuotes) { quote ->
                if (toggleFilterFavorites && !quote.isFavorite) return@items

                QuoteListItem(
                    quote = quote,
                    onQuoteClick = onQuoteSelected,
                    onFavoriteClick = { quote ->
                        onFavorite(quote.id)
                    },
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

    }
}


class QuoteListProvider : PreviewParameterProvider<List<Quote>> {
    override val values: Sequence<List<Quote>>
        get() = sequenceOf()
}


@Preview(showSystemUi = true)
@Composable
fun QuoteListScreenPreview() {
    QuotesTheme {
        QuoteListScreen(emptyList())
    }
}
