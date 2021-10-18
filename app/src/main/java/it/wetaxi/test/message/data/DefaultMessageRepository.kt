package it.wetaxi.test.message.data

import it.wetaxi.test.message.api.MessageService
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class DefaultMessageRepository @Inject constructor(
    private val messageDao: MessageDao,
    private val messageService: MessageService
) : MessageRepository {
    override fun getAllMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages()
    }

    override suspend fun getMassage(): Result<Message> {
        val response = try {
            messageService.getMessage()
        } catch (e: HttpException) {
            return Result.Error(e)
        }

        if (response.status == ResponseStatus.error) {
            return Result.Error()
        }

        messageDao.addMessage(response.message)

        return Result.Success(response.message)
    }

    override suspend fun markAllMessagesRead() {
        messageDao.markAllMessagesRead()
    }


}