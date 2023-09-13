package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.my.ui.state.signout.SignOutState
import com.zipdabang.zipdabang_android.module.my.ui.state.my.MyUserInfoState
import com.zipdabang.zipdabang_android.module.my.use_case.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.CancellationException
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val dataStore: DataStore<Token>,
    private val signOutUseCase: SignOutUseCase
) : ViewModel(){

    var stateMyUserInfo by mutableStateOf(MyUserInfoState())

    private val _signOutState = MutableStateFlow(SignOutState())
    val signOutState: StateFlow<SignOutState> = _signOutState

    fun signOut(
        onSignOutSuccessful: () -> Unit
    ) {
        signOutUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _signOutState.value = SignOutState(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    Log.d("Logout", resource.data.toString())
                    try {
                        _signOutState.value = SignOutState(
                            isLoading = false,
                            isSuccessful = true
                        )

                        dataStore.updateData {
                            it.copy(
                                accessToken = null,
                                refreshToken = null,
                                platformToken = null,
                                platformStatus = CurrentPlatform.NONE
                            )
                        }

                        onSignOutSuccessful()
                    } catch (e: Exception) {
                        if (e is CancellationException) {
                            throw e
                        } else {
                            _signOutState.value = SignOutState(
                                isLoading = false,
                                errorMessage = e.message,
                                isSuccessful = false
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    Log.d("Logout", "error ${resource.code} : ${resource.message}")
                    _signOutState.value = SignOutState(
                        isLoading = false,
                        errorMessage = resource.message,
                        isSuccessful = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}