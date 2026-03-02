package es.rafapuig.pmdm.quotesapp.domain

import androidx.compose.runtime.mutableStateListOf
import es.rafapuig.pmdm.quotesapp.data.QuoteProvider
import es.rafapuig.pmdm.quotesapp.domain.model.Quote

object QuoteManager {
    val quotes = mutableStateListOf(
        *QuoteProvider.programmingQuotes.toTypedArray()
    )

    fun toggleFavorite(id: Int) {
        val index = quotes.indexOfFirst { it.id == id }
        if (index != -1) {
             quotes[index].let {
                 quotes[index] = it.copy(isFavorite = !it.isFavorite)
             }
        }
    }

    fun filteredQuotes(query: String): List<Quote> =
        if (query.isNotBlank()) {
            quotes.filter {
                it.author.contains(query, ignoreCase = true) ||
                        it.text.contains(query, ignoreCase = true)
            }
        } else {
            quotes
        }

    fun getQuoteById(id: Int): Quote? {
        return quotes.find { it.id == id }
    }



}