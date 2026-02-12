package es.rafapuig.pmdm.quotesapp.data.repository

import es.rafapuig.pmdm.quotesapp.data.local.QuoteDao
import es.rafapuig.pmdm.quotesapp.data.local.toDomain
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.map

class QuoteRepositoryImpl(private val dao: QuoteDao) : QuoteRepository {
    override fun getQuotes() = dao.getAllQuotes().map { it.map { q -> q.toDomain() } }
    override suspend fun toggleFavorite(quote: Quote) = dao.toggleFavorite(quote.id)
    override fun searchQuotes(query: String) = dao.searchQuotes(query).map { it.map { q -> q.toDomain() } }
    override fun getQuoteById(id: Int) = dao.getQuoteById(id).map { it.toDomain() }
    override fun getRandomQuote() = dao.getRandomQuote().map { it.toDomain() }
}
