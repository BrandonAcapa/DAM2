package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository

class GetQuoteUseCase(private val repository: QuoteRepository) {
    operator fun invoke(id: Int) = repository.getQuoteById(id)
}