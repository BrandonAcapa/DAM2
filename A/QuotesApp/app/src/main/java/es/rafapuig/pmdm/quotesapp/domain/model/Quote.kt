package es.rafapuig.pmdm.quotesapp.domain.model

data class Quote(
    val id: Int,
    val text: String,
    val author: String,
    val isFavorite: Boolean = false
)