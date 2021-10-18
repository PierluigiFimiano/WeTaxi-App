package it.wetaxi.test.message

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import it.wetaxi.test.message.data.Message
import it.wetaxi.test.message.data.MessageRepository
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class WeFirebaseMessagingService : FirebaseMessagingService() {

    private lateinit var coroutineScope: CoroutineScope

    @Inject
    lateinit var repository: MessageRepository

    override fun onCreate() {
        super.onCreate()
        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        coroutineScope.launch {
            val message = Message.from(remoteMessage.data)
            repository.addMassages(message)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("Refreshed token: $token")
    }

}
