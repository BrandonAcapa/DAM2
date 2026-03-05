package es.rafapuig.pmdm.quotesapp.presentation.screens.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import es.rafapuig.pmdm.quotesapp.data.QuoteProvider
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.presentation.screens.details.components.QuoteDetails
import es.rafapuig.pmdm.quotesapp.presentation.components.TopAppBarWithBackNavigationButton
import es.rafapuig.pmdm.quotesapp.ui.theme.QuotesTheme

@Composable
fun QuoteDetailsScreen(
    quote: Quote?,
    onAction: (QuoteDetailsAction) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarWithBackNavigationButton(
                title = "Cita de ${quote?.author ?: ""}",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        quote?.let { currentQuote ->
            QuoteDetails(
                quote = currentQuote,
                modifier = Modifier.padding(innerPadding),
                onAction = { onAction(QuoteDetailsAction.OnFavoriteClick) },
                onShareClick = { onAction(QuoteDetailsAction.OnShareClick) }
            )
        } ?: run {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Cita no encontrada")
            }
        }
    }
}

@Composable
fun ErrorScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error, no se ha encontrado la cita")
    }
}

@Preview(showSystemUi = true)
@Composable
fun QuoteDetailsScreenPreview() {
    QuotesTheme {
        QuoteDetailsScreen(
            quote = QuoteProvider.programmingQuotes.first(),
            onAction = {},
            onBack = {}
        )
    }
}

