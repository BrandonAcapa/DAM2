package es.rafapuig.pmdm.quotesapp.presentation.screens

import es.rafapuig.pmdm.quotesapp.domain.model.Quote

sealed class QuoteListAction {
    data class OnQueryChange(val query: String) : QuoteListAction()
    data class OnToggleFavorite(val quoteId: Int) : QuoteListAction()
    object OnRandomQuote : QuoteListAction()
    object OnToggleFilterFavorites : QuoteListAction()
    object OnToggleSearchBar : QuoteListAction()
    data class OnQuoteSelected(val quote: Quote) : QuoteListAction()
    object OnAbout : QuoteListAction()
}
