package es.rafapuig.pmdm.quotesapp.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import es.rafapuig.pmdm.quotesapp.data.QuoteProvider
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.presentation.LocalOnNavigationBack
import es.rafapuig.pmdm.quotesapp.presentation.components.QuoteDetails
import es.rafapuig.pmdm.quotesapp.presentation.components.TopAppBarWithBackNavigationButton
import es.rafapuig.pmdm.quotesapp.presentation.theme.QuotesTheme
import es.rafapuig.pmdm.quotesapp.presentation.utils.share

@Composable
fun QuoteDetailsScreen(
    quote: Quote?,
    onFavorite: (Int) -> Unit = {},
    onBack: () -> Unit = LocalOnNavigationBack.current
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarWithBackNavigationButton(
                title = "Cita de ${quote?.author}",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        val isLandscape =
            LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

        val modifier = if (isLandscape)
            Modifier.padding(innerPadding) else Modifier

        quote?.let { quote ->
            val context = LocalContext.current
            QuoteDetails(
                quote = quote,
                modifier = Modifier.padding(innerPadding),
                onFavoriteClick = onFavorite,
                onShareClick = { quote -> quote.share(context) }
            )
        } ?: run {
            ErrorScreen()
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
            QuoteProvider.getQuoteById(1) // null
        )
    }
}



