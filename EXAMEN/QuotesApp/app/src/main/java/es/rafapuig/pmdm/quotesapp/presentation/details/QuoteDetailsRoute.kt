package es.rafapuig.pmdm.quotesapp.presentation.details

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.padding
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import es.rafapuig.pmdm.quotesapp.presentation.components.TopAppBarWithBackNavigationButton
import es.rafapuig.pmdm.quotesapp.presentation.components.QuoteDetails
import es.rafapuig.pmdm.quotesapp.presentation.ObserveAsEvents
import es.rafapuig.pmdm.quotesapp.presentation.utils.share

@Composable
fun QuoteDetailsRoute(
    viewModel: QuoteDetailsViewModel,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current

    val quoteState = viewModel.quote.collectAsStateWithLifecycle()

    viewModel.events.ObserveAsEvents { event ->
        when (event) {
            is OnShare -> event.quote.share(context)
        }
    }

    androidx.compose.material3.Scaffold(
        topBar = {
            TopAppBarWithBackNavigationButton(
                title = "Cita",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        quoteState.value?.let { quote ->
            QuoteDetails(
                quote = quote,
                modifier = androidx.compose.ui.Modifier.padding(innerPadding),
                onFavoriteClick = { id -> viewModel.onAction(es.rafapuig.pmdm.quotesapp.presentation.details.QuoteDetailsAction.OnFavoriteClick) },
                onShareClick = { q -> viewModel.onAction(es.rafapuig.pmdm.quotesapp.presentation.details.QuoteDetailsAction.OnShareClick) }
            )
        } ?: run {
            androidx.compose.material3.Text(text = "Error, no se ha encontrado la cita")
        }
    }
}
