package es.rafapuig.pmdm.quotesapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetQuoteUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class QuoteDetailsViewModel(
    private val getQuoteUseCase: GetQuoteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val quoteId: Int
) : ViewModel() {

    val quote: StateFlow<es.rafapuig.pmdm.quotesapp.domain.model.Quote?> =
        getQuoteUseCase(quoteId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _events = Channel<QuoteDetailsEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onAction(action: es.rafapuig.pmdm.quotesapp.presentation.details.QuoteDetailsAction) {
        when (action) {
            es.rafapuig.pmdm.quotesapp.presentation.details.QuoteDetailsAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    quote.value?.let { toggleFavoriteUseCase(it.id) }
                }
            }
            es.rafapuig.pmdm.quotesapp.presentation.details.QuoteDetailsAction.OnShareClick -> {
                viewModelScope.launch {
                    quote.value?.let { _events.send(OnShare(it)) }
                }
            }
        }
    }
}
