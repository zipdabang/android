package com.zipdabang.zipdabang_android.core.data_store.test

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.common.Constants.TOKEN_NULL
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.data_store.proto.tokenDataStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun DataStoreTestScreen(
    dataStore: DataStore<Token>
) {

    val viewModel = hiltViewModel<ProtoDataViewModel>()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var accessToken by remember {
        mutableStateOf(TOKEN_NULL)
    }

    LaunchedEffect(key1 = dataStore.data) {
        dataStore.data.map { tokens ->
            tokens.accessToken ?: TOKEN_NULL
        }.collect { accessToken = it }
    }

    val tokens = viewModel.tokens.collectAsState(initial = Token(
        null,
        null,
        null,
        CurrentPlatform.NONE,
        null,
        null
    )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val TAG = "DataStoreTestScreen"


        Text(text = "value ${tokens.value}")

        Button(onClick = {
            viewModel.updateAccessToken("new_access_token")
            Log.d(TAG, "")
        }) {
            Text(text = "ACCESS TOKEN ${accessToken}")
        }

        Button(onClick = {
            viewModel.updateRefreshToken("new_refresh_token")
            Log.d(TAG, "${tokens.value}")
        }) {
            Text(text = "REFRESH TOKEN")
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
            Text(text = "PLATFORM To Kakao")
        }

        Button(onClick = {
            viewModel.updatePlatformToken("platform_token")
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