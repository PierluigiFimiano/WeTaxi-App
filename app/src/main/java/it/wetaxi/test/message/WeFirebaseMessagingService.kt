package it.wetaxi.test.message

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import it.wetaxi.test.message.data.MessageRepository
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class WeFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var repository: MessageRepository

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.notification?.let { it ->
            Timber.d("${it.tag} ${it.title} ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Timber.d("Refreshed token: $token")
    }
}
