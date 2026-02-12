package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow

class GetFilteredQuotesUseCase(
    private val repository: QuoteRepository
) {
    operator fun invoke(query: String): Flow<List<Quote>> = repository.searchQuotes(query)
}
