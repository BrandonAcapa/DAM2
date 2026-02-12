package es.rafapuig.pmdm.quotesapp.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetFilteredQuotesUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetRandomQuoteUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class QuoteListViewModel(
    private val getFilteredQuotesUseCase: GetFilteredQuotesUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuoteListUiState())
    val uiState: StateFlow<QuoteListUiState> = _uiState.asStateFlow()

    private val _eventChannel = MutableSharedFlow<QuoteListEvent>()
    val events = _eventChannel.asSharedFlow()

    fun onAction(action: QuoteListAction) {
        when(action) {
            is QuoteListAction.OnQueryChange -> {
                _uiState.update { it.copy(query = action.query) }
                filterQuotes(action.query)
            }
            is QuoteListAction.OnToggleFavorite -> {
                viewModelScope.launch {
                    val quote = uiState.value.quotes.first { it.id == action.quoteId }
                    toggleFavoriteUseCase(quote)
                }
            }
            is QuoteListAction.OnRandomQuote -> {
                viewModelScope.launch {
                    getRandomQuoteUseCase().collect { quote ->
                        _eventChannel.emit(QuoteListEvent.OnNavigateToQuoteDetails(quote.id))
                    }
                }
            }
            is QuoteListAction.OnToggleFilterFavorites -> {
                _uiState.update { it.copy(showFilterFavorites = !it.showFilterFavorites) }
            }
            is QuoteListAction.OnToggleSearchBar -> {
                _uiState.update { it.copy(showSearchBar = !it.showSearchBar) }
            }
            is QuoteListAction.OnQuoteSelected -> {
                viewModelScope.launch {
                    _eventChannel.emit(QuoteListEvent.OnNavigateToQuoteDetails(action.quote.id))
                }
            }
            is QuoteListAction.OnAbout -> {
                viewModelScope.launch {
                    _eventChannel.emit(QuoteListEvent.OnNavigateToAbout)
                }
            }
        }
    }

    private fun filterQuotes(query: String) {
        viewModelScope.launch {
            getFilteredQuotesUseCase(query)
                .collect { filtered ->
                    _uiState.update { it.copy(quotes = filtered) }
                }
        }
    }
}
