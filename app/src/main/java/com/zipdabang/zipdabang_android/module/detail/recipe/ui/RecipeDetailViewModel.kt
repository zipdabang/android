package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.DeviceSize
import com.zipdabang.zipdabang_android.module.detail.recipe.use_case.GetRecipeDetailUseCase
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleLikeUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleScrapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeDataUseCase: GetRecipeDetailUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val toggleScrapUseCase: ToggleScrapUseCase,
    @DeviceSize private val deviceSize: DeviceScreenSize
) : ViewModel() {

    companion object {
        const val TAG = "RecipeDetailViewModel"
    }

    private val _recipeDetailState = mutableStateOf(RecipeDetailState())
    val recipeDetailState: State<RecipeDetailState> = _recipeDetailState

    private val _toggleLikeState = mutableStateOf(PreferenceToggleState())
    val toggleLikeState: State<PreferenceToggleState> = _toggleLikeState

    private val _toggleScrapState = mutableStateOf(PreferenceToggleState())
    val toggleScrapState: State<PreferenceToggleState> = _toggleScrapState

    fun getRecipeDetail(recipeId: Int) {
        Log.d(TAG, "get recipe detail start")
        getRecipeDataUseCase(recipeId).onEach { result ->
            result.data?.let {
                when (result) {
                    is Resource.Loading -> {
                        _recipeDetailState.value = RecipeDetailState(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _recipeDetailState.value = RecipeDetailState(
                            isLoading = false,
                            recipeDetailData = it
                        )
                    }
                    is Resource.Error -> {
                        _recipeDetailState.value = RecipeDetailState(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun toggleLike(recipeId: Int) {
        toggleLikeUseCase(recipeId).onEach { result ->
            result.data?.let {
                when (result) {
                    is Resource.Loading -> {
                        _toggleLikeState.value = PreferenceToggleState(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _toggleLikeState.value = PreferenceToggleState(
                            isLoading = false,
                            isSuccessful = true
                        )
                    }
                    is Resource.Error -> {
                        _toggleLikeState.value = PreferenceToggleState(
                            isLoading = false,
                            errorMessage = it.message,
                            isSuccessful = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun toggleScrap(recipeId: Int) {
        toggleScrapUseCase(recipeId).onEach { result ->
            result.data?.let {
                when (result) {
                    is Resource.Loading -> {
                        _toggleScrapState.value = PreferenceToggleState(
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _toggleScrapState.value = PreferenceToggleState(
                            isLoading = false,
                            isSuccessful = true
                        )

                    }
                    is Resource.Error -> {
                        _toggleScrapState.value = PreferenceToggleState(
                            isLoading = false,
                            errorMessage = it.message,
                            isSuccessful = false
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDeviceSize(): DeviceScreenSize {
        return deviceSize
    }
}