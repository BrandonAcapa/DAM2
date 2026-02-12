package es.rafapuig.pmdm.quotesapp.domain.repository

import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getQuotes(): Flow<List<Quote>>
    suspend fun toggleFavorite(quote: Quote)
    fun searchQuotes(query: String): Flow<List<Quote>>
    fun getQuoteById(id: Int): Flow<Quote?>
    fun getRandomQuote(): Flow<Quote>
}