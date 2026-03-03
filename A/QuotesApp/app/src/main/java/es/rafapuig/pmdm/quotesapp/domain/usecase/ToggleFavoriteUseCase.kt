package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository

class ToggleFavoriteUseCase(private val respository: QuoteRepository) {
    suspend operator fun invoke(quote: Quote) = respository.toggleFavorite(quote)
}