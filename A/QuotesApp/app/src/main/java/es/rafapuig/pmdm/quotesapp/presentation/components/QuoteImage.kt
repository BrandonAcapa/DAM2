package es.rafapuig.pmdm.quotesapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import es.rafapuig.pmdm.quotesapp.domain.model.Quote

@Composable
fun QuoteImage(
    quote: Quote
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data("https://picsum.photos/id/${quote.id}/1200")
            .crossfade(true)
            .build()
    )
    val state by painter.state.collectAsState()

    when (state) {
        is AsyncImagePainter.State.Success -> {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
            )
        }
        is AsyncImagePainter.State.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(.5f),
                strokeWidth = 12.dp
            )
        }
        else -> {}
    }

}