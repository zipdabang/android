package com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.zipdabang.zipdabang_android.core.NetworkConnection
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoRepository
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.mapper.toRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.use_case.GetCategoryItemCountUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.GetOwnerItemCountUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleLikeItemUseCase
import com.zipdabang.zipdabang_android.module.recipes.use_case.ToggleScrapItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeListRepository: RecipeListRepository,
    private val protoRepository: ProtoRepository,
    private val toggleItemLikeUseCase: ToggleLikeItemUseCase,
    private val toggleItemScrapUseCase: ToggleScrapItemUseCase,
    private val getCategoryItemCountUseCase: GetCategoryItemCountUseCase,
    private val getOwnerItemCountUseCase: GetOwnerItemCountUseCase,
    private val savedState: SavedStateHandle,
    @NetworkConnection
    private val isNetworkAvailable: Boolean
) : ViewModel() {

    companion object {
        const val TAG = "RecipeListViewModel"
    }

    // 사용할 곳(UI)에서 collectAsState 적용
    val tokens = protoRepository.tokens

    private val _currentPlatform = mutableStateOf(CurrentPlatform.TEMP)
    val currentPlatform: State<CurrentPlatform>
        get() = _currentPlatform

    private val _sortBy = MutableStateFlow<String>("latest")
    val sortBy: StateFlow<String> = _sortBy

    fun setSortBy(sortBy: String) {
        _sortBy.value = sortBy
    }

    private val categoryId = MutableStateFlow<Int?>(null)

    // Allow your UI to change the primary category
    fun setCategoryId(id: Int) {
        categoryId.value = id
    }

    private val ownerType = MutableStateFlow<String?>(null)

    // Allow your UI to change the primary category
    fun setOwnerType(owner: String) {
        ownerType.value = owner
    }

    lateinit var categoryItems: Flow<PagingData<RecipeItem>>
    lateinit var ownerItems: Flow<PagingData<RecipeItem>>
/*    fun isCategoryItemsInitialized() = ::categoryItems.isInitialized
    fun isOwnerItemsInitialized() = ::ownerItems.isInitialized*/

    fun refreshCategoryItems() {
        categoryItems = combine(categoryId, sortBy) { categoryId, sortBy ->
            Pair(categoryId, sortBy)
        }.distinctUntilChanged().flatMapLatest { (categoryId, sortBy) ->
            when (categoryId) {
                0 -> {
                    val pager = recipeListRepository.getRecipeAllList(sortBy)
                    pager.flow.map { pagingData ->
                        pagingData.map {
                            Log.d(TAG, "$it")
                            it.toRecipeItem()
                        }
                    }
                }
                1 -> {
                    val pager = recipeListRepository.getRecipeCoffeeList(sortBy)
                    pager.flow.map { pagingData ->
                        pagingData.map {
                            Log.d(TAG, "$it")
                            it.toRecipeItem()
                        }
                    }
                }
                2 -> {
                    val pager = recipeListRepository.getRecipeNonCaffeineList(sortBy)
                    pager.flow.map { pagingData ->
                        pagingData.map {
                            Log.d(TAG, "$it")
                            it.toRecipeItem()
                        }
                    }
                }
                3 -> {
                    val pager = recipeListRepository.getRecipeTeaList(sortBy)
                    pager.flow.map { pagingData ->
                        pagingData.map {
                            Log.d(TAG, "$it")
                            it.toRecipeItem()
                        }
                    }
                }
                4 -> {
                    val pager = recipeListRepository.getRecipeAdeList(sortBy)
                    pager.flow.map { pagingData ->
                        pagingData.map {
                            Log.d(TAG, "$it")
                            it.toRecipeItem()
                        }
                    }
                }
                5 -> {
                    val pager = recipeListRepository.getRecipeSmoothieList(sortBy)
                    pager.flow.map { pagingData ->
                        pagingData.map {
                            Log.d(TAG, "$it")
                            it.toRecipeItem()
                        }
                    }
                }
                6 -> {
                    val pager = recipeListRepository.getRecipeFruitList(sortBy)
                    pager.flow.map { pagingData ->
                        pagingData.map {
                            Log.d(TAG, "$it")
                            it.toRecipeItem()
                        }
                    }
                }
                else -> {
                    val pager = recipeListRepository.getRecipeWellBeingList(sortBy)
                    pager.flow.map { pagingData ->
                        pagingData.map {
                            Log.d(TAG, "$it")
                            it.toRecipeItem()
                        }
                    }
                }
            }.cachedIn(viewModelScope)
        }
    }

    fun refreshOwnerItems() {
        ownerItems = combine(ownerType, sortBy) { ownerType, sortBy ->
            Pair(ownerType, sortBy)
        }.distinctUntilChanged().flatMapLatest { (ownerType, sortBy) ->
            when (ownerType) {
                OwnerType.OFFICIAL.type -> {
                    val pager = recipeListRepository.getZipdabangRecipeList(sortBy)
                    pager.flow.map { pagingData ->
                        pagingData.map {
                            Log.d(TAG, "$it")
                            it.toRecipeItem()
                        }
                    }
                }
                OwnerType.BARISTA.type -> {
                    val pager = recipeListRepository.getBaristaRecipeList(sortBy)
                    pager.flow.map { pagingData ->
                        pagingData.map {
                            Log.d(TAG, "$it")
                            it.toRecipeItem()
                        }
                    }
                }
                else -> {
                    val pager = recipeListRepository.getUserRecipeList(sortBy)
                    pager.flow.map { pagingData ->
                        pagingData.map {
                            Log.d(TAG, "$it")
                            it.toRecipeItem()
                        }
                    }
                }
            }.cachedIn(viewModelScope)
        }
    }



/*    private val _toggleLikeResult = MutableStateFlow(PreferenceToggleState())
    val toggleLikeResult: StateFlow<PreferenceToggleState>
        get() = _toggleLikeResult

    private val _toggleScrapResult = MutableStateFlow(PreferenceToggleState())
    val toggleScrapResult: StateFlow<PreferenceToggleState>
        get() = _toggleScrapResult*/

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String?> = _errorMessage



    private val _total = mutableStateOf(-1)
    val total: State<Int>
        get() = _total

    /* TODO 먼저 데이터를 가져오고(완료)
    그 후 그 데이터들을 State(RecipeItem)의 리스트로 만들어(List<RecipeItem>)
    각 아이템에 대해 데이터 뿌려주기
     */

    init {
        Log.i("RecipeListScreen", "viewmodel initialized")
        getStatus()
        refreshCategoryItems()
        refreshOwnerItems()
    }



    fun getCategoryItemCount(
        categoryId: Int
    ) {
        getCategoryItemCountUseCase(categoryId).onEach { result ->
            Log.d("total", "$result")
            _total.value = result
        }.launchIn(viewModelScope)
    }

    fun getOwnerItemCount(
        ownerType: String
    ) {
        getOwnerItemCountUseCase(ownerType).onEach { result ->
            _total.value = result
        }.launchIn(viewModelScope)
    }

    fun getRecipeListByCategory(
        categoryId: Int,
        orderBy: String = "latest"
    ): Flow<PagingData<RecipeItem>> = when (categoryId) {
        0 -> {
            val pager = recipeListRepository.getRecipeAllList(orderBy)
            pager.flow.map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeItem()
                }
            }.cachedIn(viewModelScope)

        }
        1 -> {
            val pager = recipeListRepository.getRecipeCoffeeList(orderBy)
            pager.flow.map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeItem()
                }
            }.cachedIn(viewModelScope)
        }
        2 -> {
            val pager = recipeListRepository.getRecipeNonCaffeineList(orderBy)
            pager.flow.map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeItem()
                }
            }.cachedIn(viewModelScope)
        }
        3 -> {
            val pager = recipeListRepository.getRecipeTeaList(orderBy)
            pager.flow.map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeItem()
                }
            }.cachedIn(viewModelScope)
        }
        4 -> {
            val pager = recipeListRepository.getRecipeAdeList(orderBy)
            pager.flow.map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeItem()
                }
            }.cachedIn(viewModelScope)
        }
        5 -> {
            val pager = recipeListRepository.getRecipeSmoothieList(orderBy)
            pager.flow.map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeItem()
                }
            }.cachedIn(viewModelScope)
        }
        6 -> {
            val pager = recipeListRepository.getRecipeFruitList(orderBy)
            pager.flow.map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeItem()
                }
            }.cachedIn(viewModelScope)
        }
        else -> {
            val pager = recipeListRepository.getRecipeWellBeingList(orderBy)
            pager.flow.map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeItem()
                }
            }.cachedIn(viewModelScope)
        }
    }

    fun getRecipeListByOwnerType(
        ownerType: String,
        orderBy: String = "latest"
    ): Flow<PagingData<RecipeItem>>  = when (ownerType) {
        OwnerType.OFFICIAL.type -> {
            val pager = recipeListRepository.getZipdabangRecipeList(orderBy)
            pager.flow.map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeItem()
                }
            }.cachedIn(viewModelScope)
        }
        OwnerType.BARISTA.type -> {
            val pager = recipeListRepository.getBaristaRecipeList(orderBy)
            pager.flow.map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeItem()
                }
            }.cachedIn(viewModelScope)
        }
        else -> {
            val pager = recipeListRepository.getUserRecipeList(orderBy)
            pager.flow.map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeItem()
                }
            }.cachedIn(viewModelScope)
        }
    }

    suspend fun toggleItemLike(recipeId: Int): Boolean = withContext(Dispatchers.IO) {
        toggleItemLikeUseCase(recipeId)
    }

    suspend fun toggleItemScrap(recipeId: Int): Boolean = withContext(Dispatchers.IO) {
        toggleItemScrapUseCase(recipeId)
    }


    /* TODO 리스트 아이템에서 좋아요/스크랩 변경 발생 시 동작
        api 호출뿐만 아니라 db 조작 필요
        좋아요, 스크랩 여부 토글 -> 그 결과(성공/실패)에 따른 ui 반영 */

/*    fun toggleLike(
        recipeId: Int,
        categoryId: Int?,
        ownerType: String?
    ) {
        toggleLikeListUseCase(recipeId, categoryId, ownerType).onEach { result ->
            processResult(result)
        }.launchIn(viewModelScope)
    }*/

/*    fun toggleScrap(
        recipeId: Int,
        categoryId: Int?,
        ownerType: String?
    ) {
        toggleScrapListUseCase(recipeId, categoryId, ownerType).onEach { result ->
            processResult(result)
        }.launchIn(viewModelScope)
    }*/

/*    private fun processResult(result: Resource<PreferenceToggle>) {
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
    }*/

    fun isNetworkAvailable() = isNetworkAvailable

    private fun getStatus() {
        viewModelScope.launch {
            _currentPlatform.value = tokens.first().platformStatus
        }
    }
}