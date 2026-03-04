package es.rafapuig.pmdm.quotesapp.data.local

import es.rafapuig.pmdm.quotesapp.data.local.entity.QuoteEntity
import es.rafapuig.pmdm.quotesapp.domain.model.Quote

fun QuoteEntity.toDomain(): Quote {
    return Quote(
        id = this.id,
        text = this.text,
        author = this.author,
        isFavorite = this.isFavorite
    )
}

fun Quote.toEntity(): QuoteEntity {
    return QuoteEntity(
        id = this.id,
        text = this.text,
        author = this.author,
        isFavorite = this.isFavorite
    )
}