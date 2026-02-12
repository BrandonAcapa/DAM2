package es.rafapuig.pmdm.quotesapp.data.local

import es.rafapuig.pmdm.quotesapp.data.local.entity.QuoteEntity
import es.rafapuig.pmdm.quotesapp.domain.model.Quote

fun QuoteEntity.toDomain() = Quote(id, text, author, isFavorite)
fun Quote.toEntity() = QuoteEntity(id, text, author, isFavorite)
fun Quote.toDatabase(): QuoteEntity = QuoteEntity(
    id = id,
    text = text,
    author = author,
    isFavorite = isFavorite
)