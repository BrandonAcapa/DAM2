package es.rafapuig.pmdm.quotesapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesTopAppBar(
    onRandom: () -> Unit = {},
    onSettings: () -> Unit = {},
    onAbout: () -> Unit = {},
    onSearch: () -> Unit = {},
    onMenu: () -> Unit = {},
    toggleFavorite: Boolean = false,
    onFavorite: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    var moreVertMenuExpanded by remember { mutableStateOf(false) }

    MediumTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = { onMenu }) {
                Icon(Icons.Default.Menu, contentDescription = "Buscar cita")
            }
        },
        title = { Text("Citas de Programación") },
        actions = {

            IconButton(onClick = { onRandom() }) {
                Icon(Icons.Default.Casino, contentDescription = "Cita aleatoria")
            }

            IconButton(onClick = { onFavorite() }) {
                val icon =
                    if (toggleFavorite) Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder
                Icon(icon, contentDescription = "Cita aleatoria")
            }

            IconButton(onClick = { onSearch() }) {
                Icon(Icons.Default.Search, contentDescription = "Cita aleatoria")
            }


            Box {
                IconButton(onClick = { moreVertMenuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
                }
                DropdownMenu(
                    expanded = moreVertMenuExpanded,
                    onDismissRequest = { moreVertMenuExpanded = false }
                ) {

                    DropdownMenuItem(
                        text = { Text("Ajustes") },
                        onClick = {
                            moreVertMenuExpanded = false
                            onSettings()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Acerca de") },
                        onClick = {
                            moreVertMenuExpanded = false
                            onAbout()
                        }
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun QuotesTopAppBarPreview() {
    QuotesTopAppBar()
}
