package es.rafapuig.pmdm.quotesapp.presentation.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.domain.usecase.GetQuoteUseCase
import es.rafapuig.pmdm.quotesapp.domain.usecase.ToggleFavoriteUseCase
import es.rafapuig.pmdm.quotesapp.presentation.ObserveAsEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import es.rafapuig.pmdm.quotesapp.presentation.utils.share

// ACTIONS
sealed interface QuoteDetailsAction {
    object OnFavoriteClick : QuoteDetailsAction
    object OnShareClick : QuoteDetailsAction
}

// EVENTS
sealed interface QuoteDetailsEvent {
    data class OnShare(val quote: Quote) : QuoteDetailsEvent
}

// VIEWMODEL
class QuoteDetailsViewModel(
    private val getQuoteUseCase: GetQuoteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val quoteId: Int
) : ViewModel() {

    private val _events = Channel<QuoteDetailsEvent>()
    val events = _events.receiveAsFlow()

    private var currentQuote: Quote? = null
    val uiState: StateFlow<Quote?> = getQuoteUseCase(quoteId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init{
        viewModelScope.launch {
            getQuoteUseCase(quoteId).collect {
                quote -> currentQuote = quote
            }
        }
    }

    fun onAction(action: QuoteDetailsAction) {
        when (action) {
            is QuoteDetailsAction.OnFavoriteClick -> handleFavoriteClick()
            is QuoteDetailsAction.OnShareClick -> handleShareClick()
        }
    }

    private fun handleFavoriteClick() {
        viewModelScope.launch {
            currentQuote?.let { toggleFavoriteUseCase(it) }
        }
    }

    private fun handleShareClick() {
        viewModelScope.launch {
            currentQuote?.let {
                _events.send(QuoteDetailsEvent.OnShare(it))
            }
        }
    }
}

@Composable
fun QuoteDetailsRoute(
    viewModel: QuoteDetailsViewModel,
    quoteId: Int, // Identificador de la cita
    onBack: () -> Unit // Callback para volver atrás
) {
    val context = LocalContext.current

    // 1. Observar el estado de la cita
    val quote by viewModel.uiState.collectAsStateWithLifecycle()

    // 2. Procesar eventos de un solo uso (Compartir)
    viewModel.events.ObserveAsEvents { event ->
        when (event) {
            is QuoteDetailsEvent.OnShare -> {
                event.quote.share(context)
            }
        }
    }

    // 3. Llamar a la pantalla de detalle
    // Pasamos la cita actual y delegamos las acciones al viewModel
    QuoteDetailsScreen(
        quote = quote,
        onAction = { action -> viewModel.onAction(action) },
        onBack = onBack
    )
}