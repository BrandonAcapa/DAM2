package es.rafapuig.pmdm.quotesapp.domain.usecase

import es.rafapuig.pmdm.quotesapp.domain.repository.QuoteRepository

class GetQuotesUseCase(private val repository: QuoteRepository) {
    // Llama al método del repositorio que devuelve el Flow de la lista
    operator fun invoke() = repository.getQuotes()
}