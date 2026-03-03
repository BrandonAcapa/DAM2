package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository

class GetRandomQuoteUseCase(private val repository: QuoteRepository) {
    operator fun invoke() = repository.getRandomQuote()
}