package es.rafapuig.pmdm.quotesapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import es.rafapuig.pmdm.quotesapp.R
import es.rafapuig.pmdm.quotesapp.domain.model.Quote

@Composable
fun QuoteImage(quote: Quote, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
