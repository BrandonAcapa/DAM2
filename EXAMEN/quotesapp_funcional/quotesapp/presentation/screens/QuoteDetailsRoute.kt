package es.rafapuig.pmdm.quotesapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun QuoteDetailsRoute(
    viewModel: QuoteDetailsViewModel,
    onBack: () -> Unit
) {
    val quote by viewModel.quote.collectAsState()

    QuoteDetailsScreen(
        quote = quote,
        onFavorite = { viewModel.onFavoriteClicked() }, // Aquí se llama al ViewModel
        onBack = onBack
    )
}
