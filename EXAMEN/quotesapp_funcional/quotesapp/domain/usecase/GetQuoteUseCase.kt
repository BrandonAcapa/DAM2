package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository

class GetQuoteUseCase(private val repo: QuoteRepository) {
    operator fun invoke(id: Int) = repo.getQuoteById(id)
}