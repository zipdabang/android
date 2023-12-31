package com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.common.UiState
import com.zipdabang.zipdabang_android.core.DeviceSize
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoRepository
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeBannerState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeCategoryState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipePreviewState
import com.zipdabang.zipdabang_android.module.recipes.use_case.GetHotRecipesByCategoryUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.GetRecipeBannerUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.GetRecipeCategoryUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.GetRecipePreviewUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleLikeItemUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleLikeUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleScrapItemUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleScrapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class RecipeMainViewModel @Inject constructor(
    private val protoRepository: ProtoRepository,
    private val getRecipeBannerUseCase: GetRecipeBannerUseCase,
    private val getRecipeCategoryUseCase: GetRecipeCategoryUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val toggleScrapUseCase: ToggleScrapUseCase,
    private val toggleItemLikeUseCase: ToggleLikeItemUseCase,
    private val toggleItemScrapUseCase: ToggleScrapItemUseCase,
    @DeviceSize private val deviceSize: DeviceScreenSize,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {
        const val TAG = "RecipeMainViewModel"
    }

    val tokens = protoRepository.tokens

    private val _currentPlatform = mutableStateOf(CurrentPlatform.TEMP)
    val currentPlatform: State<CurrentPlatform>
        get() = _currentPlatform

    // mutableStateOf<List<RecipeResult>>(mutableListOf())
    private val _banners = mutableStateOf(
//        savedStateHandle["banners"] ?:
        RecipeBannerState()
    )
    val banners: State<RecipeBannerState> = _banners

    private val _categoryList = mutableStateOf(
//        savedStateHandle["categories"] ?:
        RecipeCategoryState()
    )
    val categoryList: State<RecipeCategoryState> = _categoryList
    
    private val _toggleLikeResult = MutableStateFlow(PreferenceToggleState())
    val toggleLikeResult: StateFlow<PreferenceToggleState>
        get() = _toggleLikeResult

    private val _toggleScrapResult = MutableStateFlow(PreferenceToggleState())
    val toggleScrapResult: StateFlow<PreferenceToggleState>
        get() = _toggleScrapResult

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String?> = _errorMessage

    init {
        getRecipeBanners()
        getRecipeCategoryList()
        getStatus()
    }

    fun getRecipeBanners() {
        if (banners.value.banners?.isEmpty()!!) {
            getRecipeBannerUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d(TAG, "recipe banner result : $result")
                        when (result.code) {
                            ResponseCode.RESPONSE_DEFAULT.code -> {
                                result.data?.let {
                                    savedStateHandle["banners"] = it
                                    _banners.value = RecipeBannerState(
                                        isLoading = false,
                                        banners = it
                                    )
                                }
                            }

                            ResponseCode.SERVER_ERROR.code -> {
                                _banners.value = RecipeBannerState(
                                    isLoading = false,
                                    errorMessage = ResponseCode.SERVER_ERROR.message
                                )
                            }

                            else -> {
                                _banners.value = RecipeBannerState(
                                    isLoading = false,
                                    errorMessage = "unexpected error"
                                )
                            }
                        }

                    }
                    is Resource.Error -> {
                        _banners.value = RecipeBannerState(
                            isLoading = false,
                            errorMessage = result.message
                        )
                        result.message?.let {
                            _errorMessage.value = it
                        }
                    }
                    is Resource.Loading -> {
                        _banners.value = RecipeBannerState(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)

        }
    }

    fun getRecipeCategoryList() {
        if (categoryList.value.recipeCategories?.isEmpty()!!) {
            getRecipeCategoryUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d(TAG, "recipe category result : $result")
                        result.data?.let {
                            when (it.code) {
                                ResponseCode.RESPONSE_DEFAULT.code -> {
                                    savedStateHandle["categories"] = it
                                    _categoryList.value = RecipeCategoryState(
                                        isLoading = false,
                                        recipeCategories = it.categoryList
                                    )
                                }

                                ResponseCode.SERVER_ERROR.code -> {
                                    _categoryList.value = RecipeCategoryState(
                                        isLoading = false,
                                        errorMessage = ResponseCode.SERVER_ERROR.message
                                    )
                                    _errorMessage.value = it.message
                                }

                                else -> {
                                    _categoryList.value = RecipeCategoryState(
                                        isLoading = false,
                                        errorMessage = "알 수 없는 오류가 발생하였습니다."
                                    )
                                    _errorMessage.value = "알 수 없는 오류가 발생하였습니다."
                                }
                            }
                        }
                    }
                    is Resource.Error -> {
                        _categoryList.value = RecipeCategoryState(
                            isLoading = false,
                            errorMessage = result.message
                        )
                        result.message?.let {
                            _errorMessage.value = it
                        }
                    }
                    is Resource.Loading -> {
                        _categoryList.value = RecipeCategoryState(
                            isLoading = true
                        )
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    suspend fun toggleItemLike(recipeId: Int): Boolean = withContext(Dispatchers.IO) {
        toggleItemLikeUseCase(recipeId)
    }

    suspend fun toggleItemScrap(recipeId: Int): Boolean = withContext(Dispatchers.IO) {
        toggleItemScrapUseCase(recipeId)
    }

    fun toggleLike(recipeId: Int) {
        toggleLikeUseCase(recipeId).onEach { result ->
            result.data?.let {
                when (result) {
                    is Resource.Success -> {
                        Log.d(TAG, "$it")
                        when (it.code) {
                            ResponseCode.RESPONSE_DEFAULT.code -> {
                                _toggleLikeResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    isSuccessful = true
                                )
                            }

                            else -> {
                                _toggleLikeResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.SERVER_ERROR.message
                            }
                        }
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "$it")

                        _toggleLikeResult.value = PreferenceToggleState(
                            isLoading = false,
                            errorMessage = it.message,
                            isSuccessful = false
                        )
                        _errorMessage.value = it.message
                    }

                    is Resource.Loading -> {
                        Log.d(TAG, "$it")

                        _toggleLikeResult.value = PreferenceToggleState(
                            isLoading = true
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
                    is Resource.Success -> {
                        when (it.code) {
                            ResponseCode.RESPONSE_DEFAULT.code -> {
                                _toggleScrapResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    isSuccessful = true
                                )
                            }
                            else -> {
                                _toggleScrapResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _toggleScrapResult.value = PreferenceToggleState(
                            isLoading = false,
                            errorMessage = it.message,
                            isSuccessful = false
                        )
                        _errorMessage.value = it.message
                    }

                    is Resource.Loading -> {
                        _toggleScrapResult.value = PreferenceToggleState(
                            isLoading = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getDeviceSize(): DeviceScreenSize {
        return deviceSize
    }

    private fun getStatus() {
        viewModelScope.launch {
            _currentPlatform.value = tokens.first().platformStatus
        }
    }
}