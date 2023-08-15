package com.zipdabang.zipdabang_android.core.data_store

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProtoDataViewModel @Inject constructor(
    private val protoRepository: ProtoRepository
): ViewModel() {

    companion object {
        const val TAG = "ProtoDataViewModel"
    }

    // 사용할 곳(UI)에서 collectAsState 적용
    val tokens = protoRepository.tokens


    fun updatePlatformToken(platform: CurrentPlatform, platformToken: String) {
        viewModelScope.launch {
            protoRepository.updatePlatformToken(platform, platformToken)
            Log.d(TAG, "${protoRepository.tokens}")
        }
    }

    fun updateAccessToken(accessToken: String) {
        viewModelScope.launch {
            protoRepository.updateAccessToken(accessToken)
        }
    }

    fun updateRefreshToken(refreshToken: String) {
        viewModelScope.launch {
            protoRepository.updateRefreshToken(refreshToken)
        }
    }

    fun updateFcmToken(fcmToken: String) {
        viewModelScope.launch {
            protoRepository.updateFcmToken(fcmToken)
        }
    }

    fun resetToken() {
        viewModelScope.launch {
            protoRepository.resetToken()
        }
    }
}