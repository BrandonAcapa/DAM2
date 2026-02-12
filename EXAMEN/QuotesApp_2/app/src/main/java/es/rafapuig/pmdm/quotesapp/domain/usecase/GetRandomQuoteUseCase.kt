package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository

class GetRandomQuoteUseCase(private val repo: QuoteRepository) {
    operator fun invoke() = repo.getRandomQuote()
}