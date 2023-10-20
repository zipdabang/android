package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.UiState
import com.zipdabang.zipdabang_android.module.detail.recipe.use_case.CancelBlockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BlockNoticeViewModel @Inject constructor(
    private val cancelBlockUseCase: CancelBlockUseCase
): ViewModel() {

    private val _cancelState = mutableStateOf<UiState<Boolean>>(UiState())
    val cancelState: State<UiState<Boolean>> = _cancelState

    fun cancelUserBlock(ownerId: Int) {
        cancelBlockUseCase(ownerId).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _cancelState.value = UiState(
                        isLoading = true
                    )

                }
                is Resource.Success -> {
                    _cancelState.value = UiState(
                        isLoading = false,
                        isSuccessful = true,
                        data = true
                    )

                }
                is Resource.Error -> {
                    _cancelState.value = UiState(
                        isLoading = false,
                        isSuccessful = false,
                        data = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}