package es.rafapuig.pmdm.quotesapp.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetQuoteUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuoteDetailsViewModel(
    val quoteId: Int,
    private val getQuoteUseCase: GetQuoteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val quote: StateFlow<Quote?> = getQuoteUseCase(quoteId)
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, null)

    fun onFavoriteClicked() {
        viewModelScope.launch {
            quote.value?.let {
                toggleFavoriteUseCase(it)
            }
        }
    }

    fun onFavoriteClick() {
        viewModelScope.launch {
            quote.value?.let {
                toggleFavoriteUseCase(it) // Esta es suspend
            }
        }
    }


}
