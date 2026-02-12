package es.rafapuig.pmdm.quotesapp.presentation.quotes

sealed interface QuoteListEvent

data class OnNavigateToQuoteDetails(val quoteId: Int) : QuoteListEvent
