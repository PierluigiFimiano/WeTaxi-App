package it.wetaxi.test.message.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM message ORDER BY read ASC, priority DESC")
    fun getAllMessages(): Flow<List<Message>>

    @Query("UPDATE message SET read = 1")
    suspend fun markAllMessagesRead()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(message: Message)
}