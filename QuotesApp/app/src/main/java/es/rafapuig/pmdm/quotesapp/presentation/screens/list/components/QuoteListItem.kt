package es.rafapuig.pmdm.quotesapp.presentation.screens.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import es.rafapuig.pmdm.quotesapp.data.QuoteProvider
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.presentation.components.QuoteAvatar
import es.rafapuig.pmdm.quotesapp.presentation.utils.avatarColor
import es.rafapuig.pmdm.quotesapp.presentation.utils.avatarInitial


@Composable
fun QuoteListItem(
    quote: Quote,
    modifier: Modifier = Modifier,
    onQuoteClick: (Quote) -> Unit = {},
    onFavoriteClick: (Quote) -> Unit = {}
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            //.background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clickable { onQuoteClick(quote) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement
            .spacedBy(16.dp, Alignment.Start)
    ) {

        QuoteAvatar(
            text = quote.avatarInitial(),
            color = quote.avatarColor()
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = quote.text,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier.align(Alignment.End)
            ) {
                Text(
                    text = quote.author,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    if(quote.isFavorite) Icons.Default.Star else Icons.Default.StarOutline,
                    contentDescription = "Favorite",
                    modifier = Modifier.clickable {
                        onFavoriteClick(quote)
                    }
                )
            }

        }
    }
}

class QuotePreviewProvider : PreviewParameterProvider<Quote> {
    override val values: Sequence<Quote>
        get() = QuoteProvider.programmingQuotes.take(8).asSequence()

}


@Preview(showBackground = true)
@Composable
fun QuoteListITemPreview(
    @PreviewParameter(QuotePreviewProvider::class) quote: Quote
) {
    QuoteListItem(quote = quote)
}

