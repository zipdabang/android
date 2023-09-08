package com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.ResponseCode
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggle
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleLikeListUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleScrapListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeListRepository: RecipeListRepository,
    private val toggleLikeListUseCase: ToggleLikeListUseCase,
    private val toggleScrapListUseCase: ToggleScrapListUseCase,
    private val savedState: SavedStateHandle
) : ViewModel() {

    companion object {
        const val TAG = "RecipeListViewModel"
    }

    private val _toggleLikeResult = mutableStateOf(PreferenceToggleState())
    val toggleLikeResult: State<PreferenceToggleState>
        get() = _toggleLikeResult

    private val _toggleScrapResult = mutableStateOf(PreferenceToggleState())
    val toggleScrapResult: State<PreferenceToggleState>
        get() = _toggleScrapResult

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String?> = _errorMessage

    /* TODO 먼저 데이터를 가져오고(완료)
    그 후 그 데이터들을 State(RecipeItem)의 리스트로 만들어(List<RecipeItem>)
    각 아이템에 대해 데이터 뿌려주기
     */

    fun getRecipeListByCategory(
        categoryId: Int,
        orderBy: String = "latest"
    ): Flow<PagingData<RecipeItem>> {
        return recipeListRepository.getRecipeListByCategory(
            categoryId = categoryId,
            orderBy = orderBy
        )
            .flow
            .map { pagingData ->
            pagingData.map {
                Log.d(TAG, "$it")
                it.toRecipeItem()
            }
        }.cachedIn(viewModelScope)
    }

    fun getRecipeListByOwnerType(
        ownerType: String,
        orderBy: String = "latest"
    ): Flow<PagingData<RecipeItem>> {
        return recipeListRepository.getRecipeListByOwnerType(
            ownerType = ownerType,
            orderBy = orderBy
        )
    }

    /* TODO 리스트 아이템에서 좋아요/스크랩 변경 발생 시 동작
        api 호출뿐만 아니라 db 조작 필요
        좋아요, 스크랩 여부 토글 -> 그 결과(성공/실패)에 따른 ui 반영 */

    fun toggleLike(recipeId: Int) {
        toggleLikeListUseCase(recipeId).onEach { result ->
            processResult(result)
        }.launchIn(viewModelScope)
    }

    fun toggleScrap(recipeId: Int) {
        toggleScrapListUseCase(recipeId).onEach { result ->
            processResult(result)
        }.launchIn(viewModelScope)
    }

    private fun processResult(result: Resource<PreferenceToggle>) {
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

                        ResponseCode.UNAUTHORIZED_ACCESS_EXPIRED.code -> {
                            _toggleScrapResult.value = PreferenceToggleState(
                                isLoading = false,
                                errorMessage = it.message,
                                isSuccessful = false
                            )
                            _errorMessage.value = ResponseCode.UNAUTHORIZED_ACCESS_EXPIRED.message
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
    }
}