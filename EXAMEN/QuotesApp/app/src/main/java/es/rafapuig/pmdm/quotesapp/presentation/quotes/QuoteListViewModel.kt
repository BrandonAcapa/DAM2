package es.rafapuig.pmdm.quotesapp.presentation.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetFilteredQuotesUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetRandomQuoteUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuoteListViewModel(
    private val getFilteredQuotesUseCase: GetFilteredQuotesUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuoteListUiState())
    val uiState: StateFlow<QuoteListUiState> = _uiState.asStateFlow()

    private val _events = Channel<QuoteListEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        val quotesFlow = _uiState.flatMapLatest { state ->
            getFilteredQuotesUseCase(state.query)
        }

        viewModelScope.launch {
            quotesFlow.collect { quotes ->
                _uiState.update { it.copy(quotes = quotes) }
            }
        }
    }

    fun onAction(action: QuoteListAction) {
        when (action) {
            is QuoteListAction.OnRandomQuote -> {
                viewModelScope.launch {
                    getRandomQuoteUseCase().collect { quote ->
                        quote?.let { _events.send(OnNavigateToQuoteDetails(it.id)) }
                    }
                }
            }
            is QuoteListAction.OnToggleFilterFavorites -> _uiState.update { it.copy(showFilterFavorites = !it.showFilterFavorites) }
            is QuoteListAction.OnToggleSearchBar -> _uiState.update { it.copy(showSearchBar = !it.showSearchBar) }
            is QuoteListAction.OnToggleFavorite -> viewModelScope.launch { toggleFavoriteUseCase(action.quoteId) }
            is QuoteListAction.OnQueryChange -> _uiState.update { it.copy(query = action.query) }
            is QuoteListAction.OnAbout -> { /* navigation handled in Route */ }
            is QuoteListAction.OnQuoteSelected -> { /* navigation handled in Route */ }
        }
    }
}
