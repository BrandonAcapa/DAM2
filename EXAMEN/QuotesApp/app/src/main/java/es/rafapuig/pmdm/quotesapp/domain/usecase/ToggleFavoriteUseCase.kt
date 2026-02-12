package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository

class ToggleFavoriteUseCase(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(quoteId: Int) {
        repository.toggleFavorite(quoteId)
    }
}
