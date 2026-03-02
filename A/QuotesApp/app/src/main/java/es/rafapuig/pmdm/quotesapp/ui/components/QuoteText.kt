package es.rafapuig.pmdm.quotesapp.ui.components

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun QuoteText(
    quoteText :String,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Text(
        modifier = modifier
            .verticalScroll(scrollState)
            .wrapContentHeight(align = Alignment.CenterVertically)
        ,
        text = "“$quoteText”",
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
        fontStyle = FontStyle.Italic
    )
}