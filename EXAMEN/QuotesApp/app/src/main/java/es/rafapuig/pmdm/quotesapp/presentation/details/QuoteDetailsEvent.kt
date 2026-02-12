package es.rafapuig.pmdm.quotesapp.presentation.details

import es.rafapuig.pmdm.quotesapp.domain.model.Quote

sealed interface QuoteDetailsEvent

data class OnShare(val quote: Quote) : QuoteDetailsEvent
