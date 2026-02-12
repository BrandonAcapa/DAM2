package es.rafapuig.pmdm.quotesapp.data.repository

import es.rafapuig.pmdm.quotesapp.data.local.QuoteDao
import es.rafapuig.pmdm.quotesapp.data.local.toDomain
import es.rafapuig.pmdm.quotesapp.data.local.toEntity
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuoteRepositoryImpl(
    private val dao: QuoteDao
) : QuoteRepository {
    override fun getQuotes(): Flow<List<Quote>> = dao.getAllQuotes().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun toggleFavorite(quoteId: Int) {
        dao.toggleFavorite(quoteId)
    }

    override fun searchQuotes(query: String): Flow<List<Quote>> = dao.searchQuotes(query).map { list ->
        list.map { it.toDomain() }
    }

    override fun getQuoteById(id: Int): Flow<Quote?> = dao.getQuoteById(id).map { it?.toDomain() }

    override fun getRandomQuote(): Flow<Quote?> = dao.getRandomQuote().map { it?.toDomain() }
}
