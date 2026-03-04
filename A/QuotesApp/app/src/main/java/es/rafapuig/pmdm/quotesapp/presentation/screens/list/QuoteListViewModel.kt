package es.rafapuig.pmdm.quotesapp.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.domain.usecase.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ACTIONS
sealed interface QuoteListAction {
    object OnRandomQuote : QuoteListAction
    object OnAbout : QuoteListAction
    object OnToggleFilterFavorites : QuoteListAction
    object OnToggleSearchBar : QuoteListAction
    data class OnToggleFavorite(val quoteId: Int) : QuoteListAction
    data class OnQueryChange(val newQuery: String) : QuoteListAction
    data class OnQuoteSelected(val quoteId: Int) : QuoteListAction
}

// EVENTS
sealed interface QuoteListEvent {
    data class OnNavigateToQuoteDetails(val quoteId: Int) : QuoteListEvent
    object OnNavigateToAbout : QuoteListEvent
}

// ESTADO
data class QuoteListUiState(
    val query: String = "",
    val quotes: List <Quote> = emptyList(),
    val showSearchBar: Boolean = false,
    val showFilterFavorites: Boolean = false
)

// VIEWMODEL
class QuoteListViewModel(
    private val getFilteredQuotesUseCase: GetFilteredQuotesUseCase,
    private val getQuoteUseCase: GetQuoteUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuoteListUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<QuoteListEvent>()
    val events = _events.receiveAsFlow()

    init{
        _uiState.map { it.query }
            .distinctUntilChanged()
            .flatMapLatest{ query ->
                getFilteredQuotesUseCase(query)
            }
            .onEach { filteredQuotes ->
                _uiState.update { it.copy(quotes = filteredQuotes) }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: QuoteListAction) {
        when (action) {
            is QuoteListAction.OnRandomQuote -> handleRandomQuote()
            is QuoteListAction.OnAbout -> {
                viewModelScope.launch { _events.send(QuoteListEvent.OnNavigateToAbout) }
            }
            is QuoteListAction.OnToggleFilterFavorites -> {
                _uiState.update { it.copy(showFilterFavorites = !it.showFilterFavorites) }
            }
            is QuoteListAction.OnToggleSearchBar -> {
                _uiState.update { it.copy(showSearchBar = !it.showSearchBar) }
            }
            is QuoteListAction.OnToggleFavorite -> handleToggleFavorite(action.quoteId)
            is QuoteListAction.OnQueryChange -> {
                _uiState.update { it.copy(query = action.newQuery) }
            }
            is QuoteListAction.OnQuoteSelected -> {
                viewModelScope.launch {
                    _events.send(QuoteListEvent.OnNavigateToQuoteDetails(action.quoteId))
                }
            }
        }
    }

    private fun handleRandomQuote() {
        viewModelScope.launch {
            getRandomQuoteUseCase().collect { quote ->
                quote?.let { _events.send(QuoteListEvent.OnNavigateToQuoteDetails(it.id)) }
            }
        }
    }

    private fun handleToggleFavorite(id: Int) {
        viewModelScope.launch {
            getQuoteUseCase(id).first()?.let{ toggleFavoriteUseCase(it) }
        }
    }
}
