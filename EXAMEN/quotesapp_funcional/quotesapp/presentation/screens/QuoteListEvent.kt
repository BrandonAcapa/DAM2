package es.rafapuig.pmdm.quotesapp.presentation.screens

sealed class QuoteListEvent {
    data class OnNavigateToQuoteDetails(val quoteId: Int) : QuoteListEvent()
    object OnNavigateToAbout : QuoteListEvent()
}
