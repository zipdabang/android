package com.zipdabang.zipdabang_android.core.data_store.proto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun updatePlatform(platform: CurrentPlatform) {
        viewModelScope.launch {
            protoRepository.updatePlatform(platform)
            Log.d(TAG, "${protoRepository.tokens}")
        }
    }

    fun updatePlatformToken(platformToken: String) {
        viewModelScope.launch {
            protoRepository.updatePlatformToken(platformToken)
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

    fun updateDeviceNumber(deviceNumber: String) {
        viewModelScope.launch {
            protoRepository.updateDeviceNumber(deviceNumber)
        }
    }

    fun resetToken() {
        viewModelScope.launch {
            protoRepository.resetToken()
        }
    }
}