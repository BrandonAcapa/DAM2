package es.rafapuig.pmdm.quotesapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import es.rafapuig.pmdm.quotesapp.data.local.QuoteDAO
import es.rafapuig.pmdm.quotesapp.data.local.toDomain
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository


class QuoteRepositoryImpl(private val quoteDao: QuoteDAO) : QuoteRepository {

    override fun getQuotes(): Flow<List<Quote>> =
        quoteDao.getAllQuotes().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun toggleFavorite(quote: Quote) {
        quoteDao.toggleFavorite(quote.id)
    }

    override fun searchQuotes(query: String): Flow<List<Quote>> =
        quoteDao.searchQuotes(query).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getQuoteById(id: Int): Flow<Quote?> =
        quoteDao.getQuoteById(id).map { it?.toDomain() }

    override fun getRandomQuote(): Flow<Quote?> =
        quoteDao.getRandomQuote().map { it?.toDomain() }
}