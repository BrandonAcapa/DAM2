package es.rafapuig.pmdm.quotesapp.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun QuoteText(quoteText: String, modifier: Modifier = Modifier) {
    Text(text = quoteText, style = MaterialTheme.typography.bodyLarge, modifier = modifier)
}
