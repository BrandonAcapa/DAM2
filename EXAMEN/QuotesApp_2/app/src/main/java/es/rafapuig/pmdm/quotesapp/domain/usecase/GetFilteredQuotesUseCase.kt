package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository

class GetFilteredQuotesUseCase(private val repo: QuoteRepository) {
    operator fun invoke(query: String) = repo.searchQuotes(query)
}