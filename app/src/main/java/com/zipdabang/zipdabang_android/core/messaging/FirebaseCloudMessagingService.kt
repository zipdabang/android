package com.zipdabang.zipdabang_android.core.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.datastore.core.DataStore
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.main.MainActivity
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseCloudMessagingService: FirebaseMessagingService() {
    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    @Inject
    lateinit var tokenDataStore: DataStore<Token>

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            Log.d(TAG, "Message notification payload: ${remoteMessage.notification}")

            sendNotification(remoteMessage)

            // Check if data needs to be processed by long running job
/*            if (isLongRunningJob()) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                // scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }*/
        }

        // Check if message contains a notification payload.
/*        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            it.body?.let { body -> sendNotification(body) }
        }*/

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private fun isLongRunningJob() = true

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "update started")
            if (tokenDataStore.data.first().fcmToken != null) {
                tokenDataStore.updateData { tokens ->
                    Log.d(TAG, "update in progress")
                    tokens.copy(
                        fcmToken = token
                    )
                }.fcmToken ?: Constants.TOKEN_NULL
            }
        }

        // FCM registration token to app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
/*
    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance(this).beginWith(work).enqueue()
        // [END dispatch_job]
    }
*/

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val requestCode = 0
        val notificationId: Int = (System.currentTimeMillis() / 7).toInt()
        val intent = Intent(this, MainActivity::class.java)

        for (key in remoteMessage.data.keys) {
            intent.putExtra(key, remoteMessage.data.getValue(key))
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val channelName = getString(R.string.default_notification_channel_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH,
            )
            notificationManager.createNotificationChannel(channel)
        }

/*        CoroutineScope(Dispatchers.Main).launch {
            val iconBitmap = getBitmap(remoteMessage.data[""].toString())
        }*/

        val notificationBuilder =
            NotificationCompat.Builder(this@FirebaseCloudMessagingService, channelId)
                .setSmallIcon(R.drawable.zipdabanglogo_brown)
                .setContentTitle(remoteMessage.data["title"].toString())
                .setContentText(remoteMessage.data["body"].toString())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                // 메시지 클릭 시 이동할 화면 정의한 intent
                .setContentIntent(pendingIntent)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private suspend fun getBitmap(imageUrl: String): Bitmap = withContext(Dispatchers.IO) {
        val loading = ImageLoader(this@FirebaseCloudMessagingService)
        val request = ImageRequest.Builder(this@FirebaseCloudMessagingService)
            .data(imageUrl)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        (result as BitmapDrawable).bitmap
    }
}