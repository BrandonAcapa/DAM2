package es.rafapuig.pmdm.quotesapp.presentation.quotes

import es.rafapuig.pmdm.quotesapp.domain.model.Quote

sealed class QuoteListAction {
    object OnRandomQuote : QuoteListAction()
    object OnAbout : QuoteListAction()
    object OnToggleFilterFavorites : QuoteListAction()
    object OnToggleSearchBar : QuoteListAction()
    data class OnToggleFavorite(val quoteId: Int) : QuoteListAction()
    data class OnQueryChange(val query: String) : QuoteListAction()
    data class OnQuoteSelected(val quote: Quote) : QuoteListAction()
}
