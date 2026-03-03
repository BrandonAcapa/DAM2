package es.rafapuig.pmdm.quotesapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "texto")
    val text: String,

    @ColumnInfo(name = "autor")
    val author: String,

    @ColumnInfo(name = "favorito")
    val is_favorite: Boolean = false
)