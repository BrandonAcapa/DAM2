package es.rafapuig.pmdm.quotesapp.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import es.rafapuig.pmdm.quotesapp.ui.LocalOnNavigationBack
import es.rafapuig.pmdm.quotesapp.ui.components.TopAppBarWithBackNavigationButton
import es.rafapuig.pmdm.quotesapp.ui.googleLogoUrl
import es.rafapuig.pmdm.quotesapp.ui.theme.QuotesTheme

@Composable
fun AboutScreen(
    onBack: () -> Unit = LocalOnNavigationBack.current
) {
    Scaffold(
        topBar = {
            TopAppBarWithBackNavigationButton(
                title = "Acerca de",
                onBack = onBack
            )
        }
        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = googleLogoUrl,
                contentDescription = "Google Logo"
            )
            Text(text = "Acerca de la aplicación")
            Text(text = "Versión 1.0")
            Text(text = "Desarrollado por Rafael Puig")
        }
    }
}

@Preview(showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun AboutScreenPreview() {
    QuotesTheme {
        AboutScreen()
    }
}


