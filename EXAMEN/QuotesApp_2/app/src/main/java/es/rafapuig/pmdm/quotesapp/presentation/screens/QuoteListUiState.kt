package es.rafapuig.pmdm.quotesapp.presentation.screens

import es.rafapuig.pmdm.quotesapp.domain.model.Quote

data class QuoteListUiState(
    val quotes: List<Quote> = emptyList(),
    val query: String = "",
    val showSearchBar: Boolean = false,
    val showFilterFavorites: Boolean = false
) {
    fun filteredQuotes(query: String): List<Quote> {
        return quotes.filter { quote ->
            quote.text.contains(query, ignoreCase = true) ||
                    quote.author.contains(query, ignoreCase = true)
        }
    }
}
