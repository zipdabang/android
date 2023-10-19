package com.zipdabang.zipdabang_android.module.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.annotation.SuppressLint
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.datastore.core.DataStore
import androidx.navigation.compose.rememberNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.navigation.RootNavGraph
import com.zipdabang.zipdabang_android.module.main.common.FCMData
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
        // private const val NOTIFICATION_REQUEST_CODE = 1234
    }

    @Inject
    lateinit var tokenDataStore: DataStore<Token>
    private lateinit var title: String
    private lateinit var body: String
    private lateinit var targetCategory: String
    private lateinit var targetPK: String
    private lateinit var targetNotificationPK: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fcmData = intent.extras?.let {
            title = it.getString("title", "no title")
            body = it.getString("body", "no body")
            targetCategory = it.getString("targetView", "NONE")
            targetPK = it.getString("targetPK", "0")
            targetNotificationPK = it.getString("targetNotificationPK", "0")
            Log.d(TAG, "title: $title, body: $body, targetCategory: $targetCategory, " +
                    "targetPK: $targetPK, targetNotificationPK: $targetNotificationPK")

            FCMData(
                title = title,
                body = body,
                targetView = targetCategory,
                targetPK = targetPK.toInt(),
                targetNotificationPK = targetNotificationPK.toInt()
            )
        }
        
        setContent {
            ZipdabangandroidTheme {
                Log.d(TAG, "fcmData : $fcmData")
                val navController = rememberNavController()
                ZipdabangApp(navController)
                RootNavGraph(outerNavController = navController, fcmData = fcmData)
            }

            LaunchedEffect(key1 = true) {
                registerDeviceNumber()
                registerDerivedToken()
            }
        }
    }


    @SuppressLint("HardwareIds")
    private suspend fun registerDeviceNumber() {
        CoroutineScope(Dispatchers.IO).launch {
            // snapshot of the token
            val deviceNumber = tokenDataStore.data.first().deviceNumber
            Log.d(TAG, "deviceNumber is $deviceNumber")

            Log.d(TAG, "deviceNumber test starts")

            if (deviceNumber == null) {
                Log.d(TAG, "deviceNumber is null, get uuid")
                val uuid = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                Log.d(TAG, "datastore deviceNumber is null, this uuid is to be into the datastore : $uuid")
                tokenDataStore.updateData { tokens ->
                    tokens.copy(
                        deviceNumber = uuid
                    )
                }
            }
        }

    }

    private suspend fun registerDerivedToken() {
        // [START log_reg_token]
        Firebase.messaging.token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            val msg = "FCM Registration token: $token"
            Log.d(TAG, msg)

            CoroutineScope(Dispatchers.IO).launch {

                var fcmToken = "null"

                Log.d(TAG, "update started")

                fcmToken = tokenDataStore.updateData { tokens ->
                    Log.d(TAG, "update in progress")
                    tokens.copy(
                        fcmToken = token
                    )
                }.fcmToken ?: TOKEN_NULL

                Log.d(TAG, "fcm token update completed,\nfcm token is $fcmToken")
            }
        }
    }

/*    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }*/
}