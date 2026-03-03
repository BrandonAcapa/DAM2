package es.rafapuig.pmdm.quotesapp.data.local

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Insert
import es.rafapuig.pmdm.quotesapp.data.local.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDAO {
    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quotes WHERE id = :id")
    fun getQuoteById(id: Int): Flow<QuoteEntity>

    @Query("DELETE FROM quotes")
    fun deleteAllQuotes()

    @Query("SELECT COUNT(*) FROM quotes")
    fun getQuotesCount()

    @Query("SELECT * FROM quotes WHERE texto LIKE '%' || :query || '%' OR autor LIKE '%' || :query || '%'")
    fun searchQuotes(query: String): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quotes ORDER BY RANDOM() LIMIT 1")
    fun getRandomQuote(): Flow<QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quote: List<QuoteEntity>)

    @Update
    suspend fun updateQuote(quote: QuoteEntity)

    @Update
    suspend fun updateQuotes(quote: List<QuoteEntity>)

    @Query("UPDATE quotes SET favorito = NOT favorito WHERE id = :quoteId")
    suspend fun toggleFavorite(quoteId: Int)
}