package com.zipdabang.zipdabang_android.module.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.data_store.proto.tokenDataStore
import com.zipdabang.zipdabang_android.core.navigation.RootNavGraph
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val NOTIFICATION_REQUEST_CODE = 1234
    }

    @Inject
    lateinit var tokenDataStore: DataStore<Token>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZipdabangandroidTheme {
                ZipdabangApp()
                val navController = rememberNavController()
                RootNavGraph(navController = navController)

            }

            val tokenDataViewModel = hiltViewModel<ProtoDataViewModel>()

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

            // 저장소에 업데이트
            // viewModel.updateFcmToken(token)

            // Log and toast
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
            // TODO 토큰 서버에 전송 api
        }
        // [END log_reg_token]
    }

    private fun askNotificationPermission() {
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
    }


}