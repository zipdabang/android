package com.zipdabang.zipdabang_android.module.login.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.login.LoginState
import com.zipdabang.zipdabang_android.module.login.data.AuthBody
import com.zipdabang.zipdabang_android.module.login.use_case.GetGoogleAuthResultUseCase
import com.zipdabang.zipdabang_android.module.login.use_case.GetKakaoAuthResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val getKakaoAuthResultUseCase: GetKakaoAuthResultUseCase,
    private val getGoogleAuthResultUseCase: GetGoogleAuthResultUseCase
) : ViewModel() {
    companion object {
        const val TAG = "LoginViewModel"
    }

    // can ignore the warning as applicationContext survives across the activity lifecycle
    /*private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state


    suspend fun resetState() {
        _state.value = LoginState()
    }*/



    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    fun getKakaoAuthResult(body: AuthBody) {
        getKakaoAuthResultUseCase(body).onEach { result ->
            // 별 일이 없으면, 첫 번째로는 Resource.Loading이 발행되고, 두 번째로는 Resource.success가 발행.
            // 발행 중 문제가 생기면 Resource.Error 발행
            when (result) {
                is Resource.Success -> {
                    // 두 번째로 방출되는 것 처리
                    _state.value = AuthState(
                        token = result.data?.token ?: ""
                    )
                    Log.d(TAG, "success $state")
                }
                is Resource.Error -> {
                    // 오류 발생 시 처라
                    _state.value = AuthState(
                        error = result.message ?: "unexpected error"
                    )
                    Log.d(TAG, "error $state")
                }
                is Resource.Loading -> {
                    // 첫 번째 방출되는 것 처리
                    _state.value = AuthState(isLoading = true)
                    Log.d(TAG, "loading $state")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getGoogleAuthResult(body: AuthBody) {
        getGoogleAuthResultUseCase(body).onEach { result ->
            // 별 일이 없으면, 첫 번째로는 Resource.Loading이 발행되고, 두 번째로는 Resource.success가 발행.
            // 발행 중 문제가 생기면 Resource.Error 발행
            when (result) {
                is Resource.Success -> {
                    // 두 번째로 방출되는 것 처리
                    _state.value = AuthState(
                        token = result.data?.token ?: ""
                    )
                }
                is Resource.Error -> {
                    // 오류 발생 시 처라
                    _state.value = AuthState(
                        error = result.message ?: "unexpected error"
                    )
                }
                is Resource.Loading -> {
                    // 첫 번째 방출되는 것 처리
                    _state.value = AuthState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}