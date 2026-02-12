package es.rafapuig.pmdm.quotesapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.rafapuig.pmdm.quotesapp.R
import es.rafapuig.pmdm.quotesapp.ui.theme.brightAvatarColors50


@Composable
fun QuoteAvatar(
    text: String,
    color: Color,
    size: Dp = 52.dp,
    modifier: Modifier = Modifier
) {
    val condensedFont = FontFamily(
        Font(R.font.roboto_condensed_bold, weight = FontWeight.Bold)
    )
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    )
    {

        Text(
            text = text,
            modifier = Modifier
                .padding(4.dp),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = condensedFont,
            letterSpacing = (-0).sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuoteAvatarPreview() {
    QuoteAvatar("MM", brightAvatarColors50[0])
}
