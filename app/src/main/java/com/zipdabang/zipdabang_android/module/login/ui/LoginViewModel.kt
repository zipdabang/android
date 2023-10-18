package com.zipdabang.zipdabang_android.module.login.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.login.data.member.AuthBody
import com.zipdabang.zipdabang_android.module.login.use_case.GetAuthResultUseCase
import com.zipdabang.zipdabang_android.module.login.use_case.GetTempLoginResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val getAuthResultUseCase: GetAuthResultUseCase,
    private val getTempLoginResultUseCase: GetTempLoginResultUseCase,
    private val tokenDataStore: DataStore<Token>
) : ViewModel() {
    companion object {
        const val TAG = "LoginViewModel"
    }

    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    private val _tempLoginState = mutableStateOf(TempLoginState())
    val tempLoginState = _tempLoginState

    fun getAuthResult(
        platform: String,
        email: String,
        profile: String,
        tokenStoreViewModel: ProtoDataViewModel,
        onSuccess: (String, String) -> Unit,
        onRegister: (String, String) -> Unit
    ) {
        getAuthResultUseCase(email, platform).onEach { result ->
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

    fun getTempLoginResult(
        onLoginLater: () -> Unit
    ) {
        getTempLoginResultUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d(TAG, "temp login loading")

                    _tempLoginState.value = TempLoginState(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    Log.d(TAG, "temp login result : $result")
                    _tempLoginState.value = TempLoginState(
                        isLoading = false,
                        accessToken = result.data?.accessToken,
                        errorMessage = null
                    )

                    val accessToken = tempLoginState.value.accessToken
                    Log.d(TAG, "temp login access token : $accessToken")

                    if (accessToken != null) {
                        tokenDataStore.updateData {
                            it.copy(
                                accessToken = accessToken,
                                platformStatus = CurrentPlatform.TEMP
                            )
                        }

                        Log.d(TAG, "temp login successful")
                        Log.d(TAG, "access token stored : ${tokenDataStore.data.first().accessToken}")

                        onLoginLater()
                    }
                }
                is Resource.Error -> {
                    _tempLoginState.value = TempLoginState(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}