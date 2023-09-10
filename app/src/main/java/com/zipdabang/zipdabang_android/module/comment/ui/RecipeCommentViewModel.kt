package com.zipdabang.zipdabang_android.module.comment.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.module.comment.domain.RecipeCommentRepository
import com.zipdabang.zipdabang_android.module.comment.use_case.PostCommentUseCase
import com.zipdabang.zipdabang_android.module.comment.util.toRecipeCommentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RecipeCommentViewModel @Inject constructor(
    private val postCommentUseCase: PostCommentUseCase,
    private val database: Paging3Database,
    private val repository: RecipeCommentRepository
): ViewModel() {

    companion object {
        const val TAG = "RecipeCommentViewModel"
    }

    private val _postResult = MutableStateFlow(PostCommentState())
    val postResult = _postResult.asStateFlow()
    // val postResult = _postResult.collectAsState()

    fun getComments(
        recipeId: Int
    ): Flow<PagingData<RecipeCommentState>> {
        return repository.getRecipeComments(recipeId)
            .flow
            .map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "$it")
                    it.toRecipeCommentState()
                }
            }.cachedIn(viewModelScope)
    }

    fun postComment(
        recipeId: Int,
        commentItem: PostCommentContent
    ) {
        postCommentUseCase(recipeId, commentItem).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _postResult.emit(
                        PostCommentState(
                            isLoading = true
                        )
                    )
                }

                is Resource.Success -> {
                    _postResult.emit(
                        PostCommentState(
                            isLoading = false,
                            isConnectionSuccessful = true,
                            errorMessage = null,
                            isPostSuccessful = true
                        )
                    )
                }

                is Resource.Error -> {
                    _postResult.emit(
                        PostCommentState(
                            isLoading = false,
                            isConnectionSuccessful = true,
                            errorMessage = result.message,
                            isPostSuccessful = false
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}