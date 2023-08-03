package com.zipdabang.zipdabang_android.module.login

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.zipdabang.zipdabang_android.common.LoginResource
import com.zipdabang.zipdabang_android.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginViewModel() : ViewModel() {
    companion object {
        const val TAG = "LoginViewModel"
    }

    // can ignore the warning as applicationContext survives across the activity lifecycle
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state


    suspend fun resetState() {
        _state.value = LoginState()
    }
}