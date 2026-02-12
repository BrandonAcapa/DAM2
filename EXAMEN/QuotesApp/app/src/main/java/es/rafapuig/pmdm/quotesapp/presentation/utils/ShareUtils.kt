package es.rafapuig.pmdm.quotesapp.presentation.utils

import android.content.Context
import android.content.Intent
import es.rafapuig.pmdm.quotesapp.domain.model.Quote

fun Quote.share(context: Context) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "“$text” — $author")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Compartir cita")
    context.startActivity(shareIntent)
}
