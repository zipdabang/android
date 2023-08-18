package com.zipdabang.zipdabang_android.module.login.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.ProtoDataViewModel
import com.zipdabang.zipdabang_android.module.login.data.AuthBody
import com.zipdabang.zipdabang_android.module.login.use_case.GetAuthResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val getAuthResultUseCase: GetAuthResultUseCase
) : ViewModel() {
    companion object {
        const val TAG = "LoginViewModel"
    }

    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    fun getAuthResult(
        body: AuthBody,
        platform: String,
        email: String,
        profile: String,
        tokenStoreViewModel: ProtoDataViewModel,
        onSuccess: (String, String) -> Unit,
        onRegister: (String, String) -> Unit
    ) {
        getAuthResultUseCase(body, platform).onEach { result ->
            // 별 일이 없으면, 첫 번째로는 Resource.Loading이 발행되고, 두 번째로는 Resource.success가 발행.
            // 발행 중 문제가 생기면 Resource.Error 발행
            when (result) {
                is Resource.Success -> {
                    _state.value = AuthState(
                        isLoading = false,
                        zipdabangToken = result.data?.zipdabangToken,
                        error = null
                    )
                    Log.d(TAG, "success $state")

                    when (platform) {
                        Constants.PLATFORM_GOOGLE -> {
                            tokenStoreViewModel.updatePlatform(CurrentPlatform.GOOGLE)
                        }
                        Constants.PLATFORM_KAKAO -> {
                            tokenStoreViewModel.updatePlatform(CurrentPlatform.KAKAO)
                        }
                        Constants.PLATFORM_TEMP -> {
                            tokenStoreViewModel.updatePlatform(CurrentPlatform.TEMP)
                        }
                        else -> {
                            tokenStoreViewModel.updatePlatform(CurrentPlatform.NONE)
                        }
                    }

                    if (state.value.zipdabangToken != null) {
                        if (state.value.zipdabangToken!!.accessToken != null
                            && state.value.zipdabangToken!!.refreshToken != null) {
                            state.value.zipdabangToken!!.accessToken?.let {
                                tokenStoreViewModel.updateAccessToken(
                                    it
                                )
                            }
                            state.value.zipdabangToken!!.refreshToken?.let {
                                tokenStoreViewModel.updateRefreshToken(
                                    it
                                )
                            }
                            onSuccess(email, profile)
                        } else {
                            Log.d(TAG, "onRegister - no acc, ref token")
                            onRegister(email, profile)
                        }
                    } else {
                        Log.d(TAG, "onRegister - no zipdabang token data")
                        onRegister(email, profile)
                    }
                }
                is Resource.Error -> {
                    // 오류 발생 시 처라
                    _state.value = AuthState(
                        isLoading = false,
                        error = result.message ?: "unexpected error"
                    )
                    Log.d(TAG, "error ${state}")
                }
                is Resource.Loading -> {
                    // 첫 번째 방출되는 것 처리
                    _state.value = AuthState(isLoading = true)
                    Log.d(TAG, "loading ${state}")
                }
            }
        }.launchIn(viewModelScope)
    }
}