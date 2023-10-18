package com.zipdabang.zipdabang_android.module.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.module.main.use_case.DeleteNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val deleteNotificationUseCase: DeleteNotificationUseCase
): ViewModel() {

    private val _result = MutableStateFlow<Boolean?>(null)
    val result: StateFlow<Boolean?> = _result

    fun getDeleteNotificationResult(alarmId: Int) {
        deleteNotificationUseCase(alarmId).onEach {
            _result.emit(it)
        }.launchIn(viewModelScope)
    }
}