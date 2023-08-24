package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipeResult
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipePreview
import com.zipdabang.zipdabang_android.module.recipes.use_case.GetRecipeCategoryUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.GetRecipePreviewUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleLikeUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleScrapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecipeMainViewModel @Inject constructor(
    private val getRecipeCategoryUseCase: GetRecipeCategoryUseCase,
    private val getRecipePreviewUseCase: GetRecipePreviewUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val toggleScrapUseCase: ToggleScrapUseCase
): ViewModel() {

    companion object {
        const val TAG = "RecipeMainViewModel"
    }

    // mutableStateOf<List<RecipeResult>>(mutableListOf())
    private val _categoryList = mutableStateOf(RecipeCategoryState())
    val categoryList: State<RecipeCategoryState> = _categoryList

    private val _everyRecipeState = mutableStateOf(RecipePreviewState())
    val everyRecipeState: State<RecipePreviewState> = _everyRecipeState

    private val _influencerRecipeState = mutableStateOf(RecipePreviewState())
    val influencerRecipeState: State<RecipePreviewState> = _influencerRecipeState

    private val _userRecipeState = mutableStateOf(RecipePreviewState())
    val userRecipeState: State<RecipePreviewState> = _userRecipeState

    private val _toggleLikeResult = mutableStateOf(PreferenceToggleState())
    val toggleLikeResult: State<PreferenceToggleState>
        get() = _toggleLikeResult

    private val _toggleScrapResult = mutableStateOf(PreferenceToggleState())
    val toggleScrapResult: State<PreferenceToggleState>
        get() = _toggleScrapResult

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String?> = _errorMessage

    private val ownerTypeMap = mapOf(
        OwnerType.ALL.type to _everyRecipeState,
        OwnerType.INFLUENCER.type to _influencerRecipeState,
        OwnerType.USER.type to _userRecipeState
    )

    fun getRecipeCategoryList(accessToken: String) {
        getRecipeCategoryUseCase(accessToken).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        when (it.code) {
                            ResponseCode.RESPONSE_DEFAULT.code -> {
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

    fun getRecipesByOwnerType(accessToken: String, ownerType: String) {
        getRecipePreviewUseCase(
            accessToken = accessToken,
            ownerType = ownerType
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { recipePreview ->
                        when (recipePreview.code) {
                            ResponseCode.RESPONSE_DEFAULT.code -> {
                                ownerTypeMap[ownerType]?.value =
                                    RecipePreviewState(
                                        isLoading = false,
                                        data = recipePreview.recipeList
                                    )
                            }

                            ResponseCode.RESPONSE_NO_DATA.code -> {
                                ownerTypeMap[ownerType]?.value = RecipePreviewState(
                                    isLoading = false,
                                    data = null,
                                    errorMessage = ResponseCode.RESPONSE_NO_DATA.message
                                )
                                _errorMessage.value = ResponseCode.RESPONSE_NO_DATA.message
                            }
                            ResponseCode.UNAUTHORIZED_TOKEN_NOT_EXISTS.code -> {
                                ownerTypeMap[ownerType]?.value = RecipePreviewState(
                                    isLoading = false,
                                    data = null,
                                    errorMessage =
                                        ResponseCode.UNAUTHORIZED_TOKEN_NOT_EXISTS.message
                                )
                                _errorMessage.value =
                                    ResponseCode.UNAUTHORIZED_TOKEN_NOT_EXISTS.message

                                // TODO 온보딩으로 보내기

                            }
                            else -> {

                            }
                        }
                    }

                }

                is Resource.Error -> {
                    ownerTypeMap[ownerType]?.value = RecipePreviewState(
                        isLoading = false,
                        errorMessage = result.data?.message
                    )

                    result.data?.let {
                        _errorMessage.value = it.message
                    }
                }

                is Resource.Loading -> {
                    ownerTypeMap[ownerType]?.value = RecipePreviewState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }



    fun toggleLike(accessToken: String, recipeId: Int) {
        toggleLikeUseCase(accessToken, recipeId).onEach { result ->
            result.data?.let {
                when (result) {
                    is Resource.Success -> {
                        when (it.code) {
                            ResponseCode.RESPONSE_DEFAULT.code -> {
                                _toggleLikeResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    isSuccessful = true
                                )
                            }

                            ResponseCode.UNAUTHORIZED_TOKEN_UNUSUAL.code -> {
                                _toggleLikeResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.UNAUTHORIZED_TOKEN_UNUSUAL.message
                            }

                            ResponseCode.UNAUTHORIZED_TOKEN_EXPIRED.code -> {
                                _toggleLikeResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.UNAUTHORIZED_TOKEN_EXPIRED.message
                            }

                            ResponseCode.UNAUTHORIZED_TOKEN_NOT_EXISTS.code -> {
                                _toggleLikeResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.UNAUTHORIZED_TOKEN_NOT_EXISTS.message
                            }

                            ResponseCode.BAD_REQUEST_USER_NOT_EXISTS.code -> {
                                _toggleLikeResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.BAD_REQUEST_USER_NOT_EXISTS.message
                            }

                            ResponseCode.BAD_REQUEST_RECIPE_NOT_EXISTS.code -> {
                                _toggleLikeResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.BAD_REQUEST_RECIPE_NOT_EXISTS.message
                            }

                            ResponseCode.SERVER_ERROR.code -> {
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
                        _toggleLikeResult.value = PreferenceToggleState(
                            isLoading = false,
                            errorMessage = it.message,
                            isSuccessful = false
                        )
                        _errorMessage.value = it.message
                    }

                    is Resource.Loading -> {
                        _toggleLikeResult.value = PreferenceToggleState(
                            isLoading = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun toggleScrap(accessToken: String, recipeId: Int) {
        toggleScrapUseCase(accessToken, recipeId).onEach { result ->
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

                            ResponseCode.UNAUTHORIZED_TOKEN_UNUSUAL.code -> {
                                _toggleScrapResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.UNAUTHORIZED_TOKEN_UNUSUAL.message
                            }

                            ResponseCode.UNAUTHORIZED_TOKEN_EXPIRED.code -> {
                                _toggleScrapResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.UNAUTHORIZED_TOKEN_EXPIRED.message
                            }

                            ResponseCode.UNAUTHORIZED_TOKEN_NOT_EXISTS.code -> {
                                _toggleScrapResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.UNAUTHORIZED_TOKEN_NOT_EXISTS.message
                            }

                            ResponseCode.BAD_REQUEST_USER_NOT_EXISTS.code -> {
                                _toggleScrapResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.BAD_REQUEST_USER_NOT_EXISTS.message
                            }

                            ResponseCode.BAD_REQUEST_RECIPE_NOT_EXISTS.code -> {
                                _toggleScrapResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.BAD_REQUEST_RECIPE_NOT_EXISTS.message
                            }

                            ResponseCode.SERVER_ERROR.code -> {
                                _toggleScrapResult.value = PreferenceToggleState(
                                    isLoading = false,
                                    errorMessage = it.message,
                                    isSuccessful = false
                                )
                                _errorMessage.value = ResponseCode.SERVER_ERROR.message
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
}