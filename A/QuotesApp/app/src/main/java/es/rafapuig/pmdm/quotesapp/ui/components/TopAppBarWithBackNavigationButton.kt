package es.rafapuig.pmdm.quotesapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithBackNavigationButton(
    title: String,
    onBack: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { onBack() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atrás"
                )
            }
        },
        title = { Text(title) }
    )
}