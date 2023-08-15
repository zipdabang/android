package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthSharedViewModel @Inject constructor(savedStateHandle: SavedStateHandle): ViewModel() {

    companion object {
        const val TAG = "AuthSharedViewModel"
    }

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    // 다른 회원정보들 dto로 만들어서 담기?

    fun updateEmail(email: String) {
        _email.value = email
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "cleared")
    }
}