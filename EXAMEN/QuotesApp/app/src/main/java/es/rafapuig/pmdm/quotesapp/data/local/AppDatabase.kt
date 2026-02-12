package es.rafapuig.pmdm.quotesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.rafapuig.pmdm.quotesapp.data.local.entity.QuoteEntity

@Database(entities = [QuoteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}

fun Context.getDatabase(): AppDatabase {
    return Room.databaseBuilder(
        this,
        AppDatabase::class.java,
        "quotes.db"
    ).build()
}

fun Context.getInMemoryDatabase(): AppDatabase {
    return Room.inMemoryDatabaseBuilder(
        this,
        AppDatabase::class.java
    ).build()
}
