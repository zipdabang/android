package com.zipdabang.zipdabang_android.module.splash.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.main.common.FCMData
import com.zipdabang.zipdabang_android.module.splash.data.AutoLoginDto
import com.zipdabang.zipdabang_android.module.splash.data.NewTokenDto
import com.zipdabang.zipdabang_android.module.splash.use_case.CheckAccessTokenUseCase
import com.zipdabang.zipdabang_android.module.splash.use_case.GetNewTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkAccessTokenUseCase: CheckAccessTokenUseCase,
    private val getNewTokenUseCase: GetNewTokenUseCase,
    private val tokenDataStore: DataStore<Token>
): ViewModel() {

    private val _checkAccessTokenState = mutableStateOf(CheckAccessTokenState())
    val checkAccessTokenState: State<CheckAccessTokenState>
        get() = _checkAccessTokenState

    private val _newTokenState = mutableStateOf(NewTokenState())
    val newTokenState: State<NewTokenState>
        get() = _newTokenState

    fun checkAccessToken(
        onTokenValid: () -> Unit,
        onTokenInvalid: () -> Unit,
        onNotificationClick: () -> Unit,
        fcmData: FCMData?
    ) {
        checkAccessTokenUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _checkAccessTokenState.value = CheckAccessTokenState(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    checkAccessTokenResult(result.data)
                    checkAccessTokenState.value.apply {
                        if (isCheckSuccessful == true) {
                            if (isAccessTokenValid == true) {
/*                                if (fcmData != null) {
                                    onNotificationClick()
                                } else {*/
                                    onTokenValid()
/*                                }*/
                                return@apply
                            }

                            getNewToken(
                                onTokenValid = onTokenValid,
                                onTokenInvalid = onTokenInvalid,
                                fcmData = fcmData,
                                onNotificationClick = onNotificationClick
                            )
                        } else {
                            onTokenInvalid()
                        }
                    }
                }

                is Resource.Error -> {
                    onTokenInvalid()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getNewToken(
        onTokenValid: () -> Unit,
        onTokenInvalid: () -> Unit,
        fcmData: FCMData?,
        onNotificationClick: () -> Unit
    ) {
        getNewTokenUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _newTokenState.value = NewTokenState(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    checkNewTokenResult(result.data)
                    newTokenState.value.apply {
                        if (isRefreshTokenValid == true && newTokens != null) {
                            // refresh 토큰 아직 만료x
                            tokenDataStore.updateData {
                                it.copy(
                                    accessToken = newTokens.accessToken,
                                    refreshToken = newTokens.refreshToken
                                )
                            }

/*                            if (fcmData != null) {
                                onNotificationClick()
                            } else {*/
                                onTokenValid()
/*
                            }
*/

                            return@apply
                        }

                        // 만료되었거나 실패
                        tokenDataStore.updateData {
                            it.copy(
                                accessToken = null,
                                refreshToken = null,
                                platformToken = null,
                                platformStatus = CurrentPlatform.NONE
                            )
                        }
                        onTokenInvalid()
                    }
                }

                is Resource.Error -> {
                    onTokenInvalid()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun checkAccessTokenResult(result: AutoLoginDto?) {
        result?.let {
            when (it.code) {
                ResponseCode.OAUTH_REMEMBER_ME_SUCCESSFUL.code -> {
                    _checkAccessTokenState.value = CheckAccessTokenState(
                        isCheckSuccessful = true,
                        isAccessTokenValid = true
                    )
                }
                ResponseCode.OAUTH_REMEMBER_ME_FAILURE.code -> {
                    _checkAccessTokenState.value = CheckAccessTokenState(
                        errorMessage = ResponseCode.OAUTH_REMEMBER_ME_FAILURE.message,
                        isCheckSuccessful = true,
                        isAccessTokenValid = false
                    )
                }
                ResponseCode.UNAUTHORIZED_ACCESS_EXPIRED.code -> {
                    _checkAccessTokenState.value = CheckAccessTokenState(
                        isLoading = true,
                        errorMessage = ResponseCode.UNAUTHORIZED_ACCESS_EXPIRED.message,
                        isCheckSuccessful = true,
                        isAccessTokenValid = false
                    )
                }
                else -> {
                    _checkAccessTokenState.value = CheckAccessTokenState(
                        errorMessage = it.message,
                        isCheckSuccessful = false
                    )
                }
            }
        } ?: run {
            _checkAccessTokenState.value = CheckAccessTokenState(
                errorMessage = "unexpected error",
                isCheckSuccessful = false
            )
        }
    }

    private fun checkNewTokenResult(result: NewTokenDto?) {
        result?.let {
            when (it.code) {
                ResponseCode.RESPONSE_DEFAULT.code -> {
                    _newTokenState.value = NewTokenState(
                        isRefreshTokenValid = true,
                        newTokens = it.result
                    )
                }
                ResponseCode.UNAUTHORIZED_REFRESH_EXPIRED.code -> {
                    _newTokenState.value = NewTokenState(
                        errorMessage = it.message,
                        isRefreshTokenValid = false
                    )
                }
                else -> {
                    _newTokenState.value = NewTokenState(
                        errorMessage = it.message,
                        isRefreshTokenValid = false
                    )
                }
            }
        } ?: run {
            _newTokenState.value = NewTokenState(
                errorMessage = "unexpected error",
                isRefreshTokenValid = false
            )
        }

        _checkAccessTokenState.value = checkAccessTokenState.value.copy(isLoading = false)
    }
}