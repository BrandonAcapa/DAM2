package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow

class GetQuoteUseCase(
    private val repository: QuoteRepository
) {
    operator fun invoke(id: Int): Flow<Quote?> = repository.getQuoteById(id)
}
