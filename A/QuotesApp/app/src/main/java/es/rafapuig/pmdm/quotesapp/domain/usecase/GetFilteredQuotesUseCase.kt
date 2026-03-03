package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository

class GetFilteredQuotesUseCase(private val repository: QuoteRepository) {
    operator fun invoke(query: String) = repository.searchQuotes(query)
}