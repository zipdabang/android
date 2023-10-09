package com.zipdabang.zipdabang_android.module.comment.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.room.withTransaction
import com.zipdabang.zipdabang_android.common.CommentMgtFailureException
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.storage.recipe.RecipeDatabase
import com.zipdabang.zipdabang_android.module.comment.domain.RecipeCommentRepository
import com.zipdabang.zipdabang_android.module.comment.use_case.BlockUserUseCase
import com.zipdabang.zipdabang_android.module.comment.use_case.DeleteCommentUseCase
import com.zipdabang.zipdabang_android.module.comment.use_case.EditCommentUseCase
import com.zipdabang.zipdabang_android.module.comment.use_case.PostCommentUseCase
import com.zipdabang.zipdabang_android.module.comment.use_case.ReportCommentUseCase
import com.zipdabang.zipdabang_android.module.comment.util.toRecipeCommentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class RecipeCommentViewModel @Inject constructor(
    private val postCommentUseCase: PostCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val editCommentUseCase: EditCommentUseCase,
    private val blockUserUseCase: BlockUserUseCase,
    private val reportCommentUseCase: ReportCommentUseCase,
    private val database: RecipeDatabase,
    private val repository: RecipeCommentRepository
): ViewModel() {

    companion object {
        const val TAG = "RecipeCommentViewModel"
    }

    private val _postResult = MutableStateFlow(PostCommentState())
    val postResult = _postResult.asStateFlow()
    // val postResult = _postResult.collectAsState()

    private val _deleteResult = MutableStateFlow(DeleteCommentState())
    val deleteResult = _deleteResult.asStateFlow()

    private val _editResult = MutableStateFlow(EditCommentState())
    val editResult = _editResult.asStateFlow()

    private val _blockResult = MutableStateFlow(BlockUserState())
    val blockResult = _blockResult.asStateFlow()

    private val _reportResult = MutableStateFlow(ReportCommentState())
    val reportResult = _reportResult.asStateFlow()

    private val _commentReportState = mutableStateOf(ReportCommentInfoState())
    val commentReportState: State<ReportCommentInfoState> = _commentReportState

    private val _isCommentReportActivated = mutableStateOf(false)
    val isCommentReportActivated: State<Boolean> = _isCommentReportActivated

    private val _isCommentBlockActivated = mutableStateOf(false)
    val isCommentBlockActivated: State<Boolean> = _isCommentBlockActivated

    private val _blockOwnerId = mutableStateOf(0)
    val blockOwnerId: State<Int> = _blockOwnerId


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
        commentItem: String
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

    fun deleteComment(
        recipeId: Int,
        commentId: Int
    ) {
        deleteCommentUseCase(recipeId, commentId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _deleteResult.emit(
                        DeleteCommentState(
                            isLoading = true
                        )
                    )
                }

                is Resource.Success -> {
                    deleteCommentLocal(commentId)
                    _deleteResult.emit(
                        DeleteCommentState(
                            isLoading = false,
                            isConnectionSuccessful = true,
                            errorMessage = null,
                            isDeleteSuccessful = true
                        )
                    )
                }

                is Resource.Error -> {
                    _deleteResult.emit(
                        DeleteCommentState(
                            isLoading = false,
                            isConnectionSuccessful = true,
                            errorMessage = result.message,
                            isDeleteSuccessful = false
                        )
                    )
                    Log.d(TAG, "${deleteResult.value}")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun editComment(
        recipeId: Int,
        commentId: Int,
        newContent: String
    ) {
        editCommentUseCase(recipeId, commentId, newContent).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _editResult.emit(
                        EditCommentState(
                            isLoading = true
                        )
                    )
                }

                is Resource.Success -> {
                    editCommentLocal(commentId = commentId, newContent = newContent)
                    _editResult.emit(
                        EditCommentState(
                            isLoading = false,
                            isConnectionSuccessful = true,
                            errorMessage = null,
                            isEditSuccessful = true
                        )
                    )
                }

                is Resource.Error -> {
                    _editResult.emit(
                        EditCommentState(
                            isLoading = false,
                            isConnectionSuccessful = true,
                            errorMessage = result.message,
                            isEditSuccessful = false
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun editCommentLocal(
        commentId: Int,
        newContent: String
    ) {
        database.withTransaction {
            try {
                val currentComment = database.recipeCommentsDao().getCommentItemById(commentId = commentId)
                    ?: throw NullPointerException()
                database.recipeCommentsDao().updateComment(
                    currentComment.copy(content = newContent)
                )
            } catch (e: NullPointerException) {
                Log.d(TAG, "no comment ${e.message}")
            }
        }
    }

    private suspend fun deleteCommentLocal(
        commentId: Int
    ) {
        database.withTransaction {
            try {
                database.recipeCommentsDao().deleteComment(commentId)
            } catch (e: NullPointerException) {
                Log.d(TAG, "no comment ${e.message}")
            }
        }
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

    fun reportComment(
        recipeId: Int,
        commentId: Int,
        reportId: Int
    ) {
        reportCommentUseCase(
            recipeId = recipeId,
            commentId = commentId,
            reportId = reportId
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _reportResult.emit(
                        ReportCommentState(
                            isLoading = false,
                            isConnectionSuccessful = true,
                            errorMessage = null,
                            isReportSuccessful = true
                        )
                    )
                }
                is Resource.Error -> {
                    _reportResult.emit(
                        ReportCommentState(
                            isLoading = false,
                            isConnectionSuccessful = true,
                            errorMessage = result.message,
                            isReportSuccessful = false
                        )
                    )
                }
                is Resource.Loading -> {
                    _reportResult.emit(
                        ReportCommentState(
                            isLoading = true
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setCommentReportDialogStatus(activated: Boolean) {
        _isCommentReportActivated.value = activated
    }

    fun setCommentBlockDialogStatus(activated: Boolean) {
        _isCommentBlockActivated.value = activated
    }

    fun setCommentReportState(commentReportState: ReportCommentInfoState) {
        _commentReportState.value = commentReportState
    }

    fun changeReportContent(reportId: Int) {
        _commentReportState.value = commentReportState.value.copy(
            reportId = reportId
        )
    }

    fun setBlockOwnerId(ownerId: Int) {
        _blockOwnerId.value = ownerId
    }
}