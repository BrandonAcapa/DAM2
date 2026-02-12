package es.rafapuig.pmdm.quotesapp.presentation.details

sealed class QuoteDetailsAction {
    object OnFavoriteClick : QuoteDetailsAction()
    object OnShareClick : QuoteDetailsAction()
}
