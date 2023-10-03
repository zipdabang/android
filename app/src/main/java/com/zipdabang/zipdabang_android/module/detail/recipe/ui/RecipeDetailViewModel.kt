package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.common.UiState
import com.zipdabang.zipdabang_android.core.DeviceSize
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.ui.BlockUserState
import com.zipdabang.zipdabang_android.module.comment.use_case.BlockUserUseCase
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.detail.recipe.use_case.GetRecipeDetailUseCase
import com.zipdabang.zipdabang_android.module.detail.recipe.use_case.ReportRecipeUseCase
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleLikeUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleScrapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeDataUseCase: GetRecipeDetailUseCase,
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val toggleScrapUseCase: ToggleScrapUseCase,
    private val reportRecipeUseCase: ReportRecipeUseCase,
    private val blockUserUseCase: BlockUserUseCase,
    private val tokens: DataStore<Token>,
    @DeviceSize private val deviceSize: DeviceScreenSize
) : ViewModel() {

    companion object {
        const val TAG = "RecipeDetailViewModel"
    }

    private val _currentPlatform = mutableStateOf<CurrentPlatform?>(CurrentPlatform.TEMP)
    val currentPlatform: State<CurrentPlatform?> = _currentPlatform

    private val _recipeDetailState = mutableStateOf(RecipeDetailState())
    val recipeDetailState: State<RecipeDetailState> = _recipeDetailState

    private val _toggleLikeState = MutableStateFlow(PreferenceToggleState())
    val toggleLikeState: StateFlow<PreferenceToggleState> = _toggleLikeState

    private val _toggleScrapState = MutableStateFlow(PreferenceToggleState())
    val toggleScrapState: StateFlow<PreferenceToggleState> = _toggleScrapState

    private val _recipeReportResult = MutableStateFlow(UiState<Boolean>())
    val recipeReportResult = _recipeReportResult.asStateFlow()

    private val _blockResult = MutableStateFlow(BlockUserState())
    val blockResult = _blockResult.asStateFlow()

    private val _isRecipeReportActivated = mutableStateOf(false)
    val isRecipeReportActivated: State<Boolean> = _isRecipeReportActivated

    private val _isRecipeBlockActivated = mutableStateOf(false)
    val isRecipeBlockActivated: State<Boolean> = _isRecipeBlockActivated

    private val _recipeReportState = mutableStateOf(RecipeReportState())
    val recipeReportState: State<RecipeReportState> = _recipeReportState

    private val _blockOwnerId = mutableStateOf(0)
    val blockOwnerId: State<Int> = _blockOwnerId

    private val _likes = MutableStateFlow(0)
    val likes = _likes.asStateFlow()

    private val _scraps = MutableStateFlow(0)
    val scraps = _scraps.asStateFlow()

    private val _isLikeChecked = MutableStateFlow(false)
    val isLikeChecked = _isLikeChecked.asStateFlow()

    private val _isScrapChecked = MutableStateFlow(false)
    val isScrapChecked = _isScrapChecked.asStateFlow()

    init {
        viewModelScope.launch {
            _currentPlatform.value = getCurrentPlatform()
        }
    }

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

                        _likes.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.likes ?: 0)
                        _scraps.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.scraps ?: 0)
                        _isLikeChecked.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.isLiked ?: false)
                        _isScrapChecked.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.isScrapped ?: false)


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

                        if (recipeDetailState.value.recipeDetailData?.recipeInfo?.isLiked == true) {
                            _recipeDetailState.value.recipeDetailData?.recipeInfo?.isLiked = false
                            _recipeDetailState.value.recipeDetailData?.recipeInfo?.likes =
                                recipeDetailState.value.recipeDetailData?.recipeInfo?.likes?.minus(
                                    1
                                )!!
                        } else {
                            recipeDetailState.value.recipeDetailData?.recipeInfo?.isLiked = true
                            _recipeDetailState.value.recipeDetailData?.recipeInfo?.likes =
                                recipeDetailState.value.recipeDetailData?.recipeInfo?.likes?.plus(
                                    1
                                )!!
                        }

                        Log.d("RecipeDetail",
                            "likes : ${recipeDetailState.value.recipeDetailData?.recipeInfo?.likes}, " +
                                    "scraps: ${recipeDetailState.value.recipeDetailData?.recipeInfo?.scraps}, " +
                                    "isLike: ${recipeDetailState.value.recipeDetailData?.recipeInfo?.isLiked}, " +
                                    "isScrapChecked: ${recipeDetailState.value.recipeDetailData?.recipeInfo?.isScrapped}"
                        )

                        _likes.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.likes ?: 0)
                        _scraps.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.scraps ?: 0)
                        _isLikeChecked.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.isLiked ?: false)
                        _isScrapChecked.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.isScrapped ?: false)


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
                        if (recipeDetailState.value.recipeDetailData?.recipeInfo?.isScrapped == true) {
                            _recipeDetailState.value.recipeDetailData?.recipeInfo?.isScrapped = false
                            _recipeDetailState.value.recipeDetailData?.recipeInfo?.scraps =
                                recipeDetailState.value.recipeDetailData?.recipeInfo?.scraps?.minus(
                                    1
                                )!!
                        } else {
                            recipeDetailState.value.recipeDetailData?.recipeInfo?.isScrapped = true
                            _recipeDetailState.value.recipeDetailData?.recipeInfo?.scraps =
                                recipeDetailState.value.recipeDetailData?.recipeInfo?.scraps?.plus(
                                    1
                                )!!
                        }

                        Log.d("RecipeDetail",
                            "likes : ${recipeDetailState.value.recipeDetailData?.recipeInfo?.likes}, " +
                                    "scraps: ${recipeDetailState.value.recipeDetailData?.recipeInfo?.scraps}, " +
                                    "isLike: ${recipeDetailState.value.recipeDetailData?.recipeInfo?.isLiked}, " +
                                    "isScrapChecked: ${recipeDetailState.value.recipeDetailData?.recipeInfo?.isScrapped}"
                        )

                        _likes.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.likes ?: 0)
                        _scraps.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.scraps ?: 0)
                        _isLikeChecked.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.isLiked ?: false)
                        _isScrapChecked.emit(recipeDetailState.value.recipeDetailData?.recipeInfo?.isScrapped ?: false)


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

    fun reportRecipe(
        recipeId: Int, reportId: Int
    ) {
        Log.i(TAG, "report start")

        reportRecipeUseCase(
            recipeId = recipeId, reportId = reportId
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.i(TAG, "report loading")
                    _recipeReportResult.emit(UiState(isLoading = true))
                }
                is Resource.Success -> {
                    Log.i(TAG, "report success")

                    _recipeReportResult.emit(
                        UiState(
                            isLoading = false,
                            errorMessage = null,
                            isSuccessful = true,
                            data = true
                        )
                    )
                }
                is Resource.Error -> {
                    Log.i(TAG, "report error")

                    _recipeReportResult.emit(
                        UiState(
                            isLoading = false,
                            errorMessage = result.message,
                            isSuccessful = false,
                            data = false
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun blockUser(
        ownerId: Int
    ) {
        blockUserUseCase(ownerId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _blockResult.emit(
                        BlockUserState(
                            isLoading = false,
                            isConnectionSuccessful = true,
                            errorMessage = null,
                            isBlockSuccessful = true
                        )
                    )
                }
                is Resource.Error -> {
                    _blockResult.emit(
                        BlockUserState(
                            isLoading = false,
                            isConnectionSuccessful = true,
                            errorMessage = result.message,
                            isBlockSuccessful = false
                        )
                    )
                }
                is Resource.Loading -> {
                    _blockResult.emit(
                        BlockUserState(
                            isLoading = true
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    // dialog
    fun setRecipeReportDialogStatus(activated: Boolean) {
        _isRecipeReportActivated.value = activated
    }

    fun setRecipeBlockDialogStatus(activated: Boolean) {
        _isRecipeBlockActivated.value = activated
    }

    fun setRecipeReportState(recipeReportState: RecipeReportState) {
        _recipeReportState.value = recipeReportState
    }

    fun changeReportContent(reportId: Int) {
        _recipeReportState.value = recipeReportState.value.copy(
            reportId = reportId
        )
    }

    fun getDeviceSize(): DeviceScreenSize {
        return deviceSize
    }

    private suspend fun getCurrentPlatform() = withContext(Dispatchers.IO) {
        tokens.data.first().platformStatus ?: CurrentPlatform.TEMP
    }

    fun changeBlockOwnerId(ownerId: Int) {
        _blockOwnerId.value = ownerId
    }
}