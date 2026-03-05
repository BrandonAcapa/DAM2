package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFilteredQuotesUseCase(private val repository: QuoteRepository) {
    operator fun invoke(query: String, favoritesOnly: Boolean): Flow<List<Quote>> {
        return repository.getQuotes().map { list ->
            list.filter { quote ->
                val matchesQuery = quote.text.contains(query, ignoreCase = true) ||
                        quote.author.contains(query, ignoreCase = true)
                val matchesFavorite = if (favoritesOnly) quote.isFavorite else true

                matchesQuery && matchesFavorite
            }
        }
    }
}