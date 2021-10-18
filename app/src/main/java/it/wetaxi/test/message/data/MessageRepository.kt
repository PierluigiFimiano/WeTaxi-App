package it.wetaxi.test.message.data

import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    fun getAllMessages(): Flow<List<Message>>

    suspend fun getMassage(): Result<Message>

    suspend fun markAllMessagesRead()
}