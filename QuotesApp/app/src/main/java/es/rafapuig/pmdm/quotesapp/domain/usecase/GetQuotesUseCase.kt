package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository

class GetQuotesUseCase(private val repository: QuoteRepository) {
    operator fun invoke() = repository.getQuotes()
}