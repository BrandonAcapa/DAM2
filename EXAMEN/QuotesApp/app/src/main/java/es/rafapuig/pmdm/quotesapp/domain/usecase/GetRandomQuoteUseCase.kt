package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow

class GetRandomQuoteUseCase(
    private val repository: QuoteRepository
) {
    operator fun invoke(): Flow<Quote?> = repository.getRandomQuote()
}
