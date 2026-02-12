package es.rafapuig.pmdm.quotesapp.presentation.quotes

import es.rafapuig.pmdm.quotesapp.domain.model.Quote

data class QuoteListUiState(
    val query: String = "",
    val quotes: List<Quote> = emptyList(),
    val showSearchBar: Boolean = false,
    val showFilterFavorites: Boolean = false
)
