package com.zipdabang.zipdabang_android.core.data_store.test

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.core.data_store.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.data_store.Token

@Composable
fun DataStoreTestScreen(
    viewModel: ProtoDataViewModel = hiltViewModel()
) {
    val tokens = viewModel.tokens.collectAsState(initial = Token(
        null,
        null,
        null,
        CurrentPlatform.NONE,
        null,
    )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val TAG = "DataStoreTestScreen"
        val scope = rememberCoroutineScope()

        Text(text = "value ${tokens.value}")

        Button(onClick = {
            viewModel.updateAccessToken("new_access_token")
            Log.d(TAG, "${tokens.value}")
        }) {
            Text(text = "ACCESS TOKEN")
        }

        Button(onClick = {
            viewModel.updateFcmToken("new_fcm_token")
            Log.d(TAG, "${tokens.value}")
        }) {
            Text(text = "FCM TOKEN")
        }

        Button(onClick = {
            viewModel.updatePlatform(CurrentPlatform.KAKAO)
            Log.d(TAG, "${tokens.value}")
        }) {
            Text(text = "PLATFORM TOKEN")
        }

        Button(onClick = {
            viewModel.resetToken()
            Log.d(TAG, "${tokens.value}")
        }) {
            Text(text = "RESET TOKEN")
        }

    }
}