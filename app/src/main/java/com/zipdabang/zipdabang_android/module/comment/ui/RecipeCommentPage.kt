package com.zipdabang.zipdabang_android.module.comment.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.module.comment.ui.components.CommentSubmit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun RecipeCommentPage(
    commentCount: Int,
    recipeId: Int,
    onClickReport: (Int, Int, Int) -> Unit,
    onClickBlock: (Int) -> Unit,
    onClickEdit: (Int, Int, String) -> Unit,
    onClickDelete: (Int, Int) -> Unit,
    showCommentReport: (Int, Int, Int) -> Unit
) {
    val viewModel = hiltViewModel<RecipeCommentViewModel>()
    val tokenViewModel = hiltViewModel<ProtoDataViewModel>()
    val postResult = viewModel.postResult.collectAsState()
    val comments =
        viewModel.getComments(recipeId).collectAsLazyPagingItems()

/*
    suspend fun currentPlatform() = withContext(Dispatchers.IO) {
        tokenViewModel.tokens.first().platformStatus
    }
*/

/*    suspend fun getCurrentPlatform(): CurrentPlatform = suspendCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            val platform = tokenViewModel.tokens.first().platformStatus
            continuation.resume(platform)
        }
    }*/

    Log.d("commentlist", "recipeId : $recipeId")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CommentListContent(
            commentCount = commentCount,
            comments = comments,
            onClickReport = onClickReport,
            onClickBlock = onClickBlock,
            onClickEdit = onClickEdit,
            onClickDelete = onClickDelete,
            postResult = postResult.value,
            recipeId = recipeId,
            showCommentReport = showCommentReport,
        )
    }
}