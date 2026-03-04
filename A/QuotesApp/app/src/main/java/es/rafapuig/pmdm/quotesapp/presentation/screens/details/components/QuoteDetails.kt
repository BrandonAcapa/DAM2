package es.rafapuig.pmdm.quotesapp.presentation.screens.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.rafapuig.pmdm.quotesapp.data.QuoteProvider
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.presentation.components.QuoteImage
import es.rafapuig.pmdm.quotesapp.presentation.components.QuoteText


@Composable
fun QuoteDetails(
    quote: Quote,
    modifier: Modifier = Modifier,
    onAction: (Int) -> Unit = {},
    onShareClick: (Quote) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement
            .spacedBy(24.dp, alignment = Alignment.Top)
    ) {
        Box(
            modifier = Modifier
                //.weight(.4f)
                .fillMaxWidth(.95f)
                .aspectRatio(1f),
            //.background(Color.Red)
            contentAlignment = Alignment.Center,

            ) {
            QuoteImage(quote = quote)
        }

        QuoteText(
            quoteText = quote.text,
            modifier = Modifier.weight(.5f)
        )

        Text(
            modifier = Modifier
                .weight(.1f)
                .align(Alignment.End)
                .wrapContentSize(align = Alignment.CenterEnd),
            text = quote.author,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SuggestionChip(
                onClick = { onAction(quote.id) },
                label = {
                    Text(text = "Favoritos")
                },
                icon = {
                    val favoriteIcon =
                        if (quote.isFavorite) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder

                    Icon(
                        imageVector = favoriteIcon,
                        contentDescription = null
                    )
                }
            )

            SuggestionChip(
                onClick = { onShareClick(quote) },
                label = {
                    Text(text = "Compartir")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null
                    )
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuoteDetailsPreview() {
    QuoteDetails(quote = QuoteProvider.programmingQuotes[1])
}