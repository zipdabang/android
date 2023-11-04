package com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.common.UiState
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.use_case.GetHotRecipesByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HotRecipeViewModel @Inject constructor(
    private val getHotRecipesByCategoryUseCase: GetHotRecipesByCategoryUseCase
):ViewModel() {
    private val _hotCoffeeRecipeState = mutableStateOf(UiState<List<HotRecipeItem>>())
    val hotCoffeeRecipeState: State<UiState<List<HotRecipeItem>>> = _hotCoffeeRecipeState

    private val _hotCaffeineFreeRecipeState = mutableStateOf(UiState<List<HotRecipeItem>>())
    val hotCaffeineFreeRecipeState: State<UiState<List<HotRecipeItem>>> = _hotCaffeineFreeRecipeState

    private val _hotTeaRecipeState = mutableStateOf(UiState<List<HotRecipeItem>>())
    val hotTeaRecipeState: State<UiState<List<HotRecipeItem>>> = _hotTeaRecipeState

    private val _hotAdeRecipeState = mutableStateOf(UiState<List<HotRecipeItem>>())
    val hotAdeRecipeState: State<UiState<List<HotRecipeItem>>> = _hotAdeRecipeState

    private val _hotSmoothieRecipeState = mutableStateOf(UiState<List<HotRecipeItem>>())
    val hotSmoothieRecipeState: State<UiState<List<HotRecipeItem>>> = _hotSmoothieRecipeState

    private val _hotFruitRecipeState = mutableStateOf(UiState<List<HotRecipeItem>>())
    val hotFruitRecipeState: State<UiState<List<HotRecipeItem>>> = _hotFruitRecipeState

    private val _hotWellBeingRecipeState = mutableStateOf(UiState<List<HotRecipeItem>>())
    val hotWellBeingRecipeState: State<UiState<List<HotRecipeItem>>> = _hotWellBeingRecipeState

    private val _hotAllRecipeState = mutableStateOf(UiState<List<HotRecipeItem>>())
    val hotAllRecipeState: State<UiState<List<HotRecipeItem>>> = _hotAllRecipeState

    /*init {
        (0..7).forEach {
            getHotRecipesByCategory(it)
        }
    }*/

    fun getHotRecipesByCategory(categoryId: Int) {
        getHotRecipesByCategoryUseCase(categoryId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    loading(categoryId)
                }
                is Resource.Success -> {
                    success(categoryId, result)
                }
                is Resource.Error -> {
                    error(categoryId, result)
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun loading(
        categoryId: Int
    ) {
        when (categoryId) {
            0 -> {
                _hotAllRecipeState.value = UiState(
                    isLoading = true
                )
            }
            1 -> {
                _hotCoffeeRecipeState.value = UiState(
                    isLoading = true
                )
            }
            2 -> {
                _hotCaffeineFreeRecipeState.value = UiState(
                    isLoading = true
                )
            }
            3 -> {
                _hotTeaRecipeState.value = UiState(
                    isLoading = true
                )
            }
            4 -> {
                _hotAdeRecipeState.value = UiState(
                    isLoading = true
                )
            }
            5 -> {
                _hotSmoothieRecipeState.value = UiState(
                    isLoading = true
                )
            }
            6 -> {
                _hotFruitRecipeState.value = UiState(
                    isLoading = true
                )
            }
            7 -> {
                _hotWellBeingRecipeState.value = UiState(
                    isLoading = true
                )
            }
        }
    }

    private fun success(
        categoryId: Int,
        result: Resource<ResponseBody<List<HotRecipeItem>>>
    ) {
        when (categoryId) {
            0 -> {
                _hotAllRecipeState.value = UiState(
                    isLoading = false,
                    isSuccessful = true,
                    data = result.data?.result ?: emptyList()
                )
            }
            1 -> {
                _hotCoffeeRecipeState.value = UiState(
                    isLoading = false,
                    isSuccessful = true,
                    data = result.data?.result ?: emptyList()
                )
            }
            2 -> {
                _hotCaffeineFreeRecipeState.value = UiState(
                    isLoading = false,
                    isSuccessful = true,
                    data = result.data?.result ?: emptyList()
                )
            }
            3 -> {
                _hotTeaRecipeState.value  = UiState(
                    isLoading = false,
                    isSuccessful = true,
                    data = result.data?.result ?: emptyList()
                )
            }
            4 -> {
                _hotAdeRecipeState.value  = UiState(
                    isLoading = false,
                    isSuccessful = true,
                    data = result.data?.result ?: emptyList()
                )
            }
            5 -> {
                _hotSmoothieRecipeState.value  = UiState(
                    isLoading = false,
                    isSuccessful = true,
                    data = result.data?.result ?: emptyList()
                )
            }
            6 -> {
                _hotFruitRecipeState.value  = UiState(
                    isLoading = false,
                    isSuccessful = true,
                    data = result.data?.result ?: emptyList()
                )
            }
            7 -> {
                _hotWellBeingRecipeState.value  = UiState(
                    isLoading = false,
                    isSuccessful = true,
                    data = result.data?.result ?: emptyList()
                )
            }
        }
    }

    private fun error(
        categoryId: Int,
        result: Resource<ResponseBody<List<HotRecipeItem>>>
    ) {
        when (categoryId) {
            0 -> {
                _hotAllRecipeState.value = UiState(
                    isLoading = false,
                    isSuccessful = false,
                    errorMessage = result.message
                )
            }
            1 -> {
                _hotCoffeeRecipeState.value  = UiState(
                    isLoading = false,
                    isSuccessful = false,
                    errorMessage = result.message
                )
            }
            2 -> {
                _hotCaffeineFreeRecipeState.value = UiState(
                    isLoading = false,
                    isSuccessful = false,
                    errorMessage = result.message
                )
            }
            3 -> {
                _hotTeaRecipeState.value  = UiState(
                    isLoading = false,
                    isSuccessful = false,
                    errorMessage = result.message
                )
            }
            4 -> {
                _hotAdeRecipeState.value = UiState(
                    isLoading = false,
                    isSuccessful = false,
                    errorMessage = result.message
                )
            }
            5 -> {
                _hotSmoothieRecipeState.value = UiState(
                    isLoading = false,
                    isSuccessful = false,
                    errorMessage = result.message
                )
            }
            6 -> {
                _hotFruitRecipeState.value = UiState(
                    isLoading = false,
                    isSuccessful = false,
                    errorMessage = result.message
                )
            }
            7 -> {
                _hotWellBeingRecipeState.value = UiState(
                    isLoading = false,
                    isSuccessful = false,
                    errorMessage = result.message
                )
            }
        }
    }
}

