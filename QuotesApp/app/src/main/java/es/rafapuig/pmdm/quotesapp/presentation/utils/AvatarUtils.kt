package es.rafapuig.pmdm.quotesapp.presentation.utils

import androidx.compose.ui.graphics.Color
import es.rafapuig.pmdm.quotesapp.domain.model.Quote
import es.rafapuig.pmdm.quotesapp.ui.theme.brightAvatarColors50
import kotlin.math.absoluteValue


fun Quote.avatarColor(colors: List<Color> = brightAvatarColors50) =
    colors[(author.hashCode().absoluteValue) % colors.size]


fun Quote.avatarInitial() =
    author
        .trim()
        .split("\\s+".toRegex())
        .filter { word -> word.isNotBlank() }
        .let { words ->
            when (words.size) {
                0 -> ""
                1 -> words.first().take(1)
                else -> "${words.first().take(1)}${words.last().take(1)}"
            }
        }.uppercase()




