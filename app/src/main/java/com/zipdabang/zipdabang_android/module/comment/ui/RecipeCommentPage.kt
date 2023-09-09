package com.zipdabang.zipdabang_android.module.comment.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.comment.ui.components.CommentSubmit

@Composable
fun RecipeCommentPage(
    commentCount: Int,
    recipeId: Int,
    onClickReport: (Int) -> Unit,
    onClickBlock: (Int) -> Unit,
    onClickEdit: (Int) -> Unit,
    onClickDelete: (Int) -> Unit
) {
    val viewModel = hiltViewModel<RecipeCommentViewModel>()
    val postResult = viewModel.postResult.collectAsState()
    val comments =
        viewModel.getComments(recipeId).collectAsLazyPagingItems()

    Log.d("commentlist", "recipeId : $recipeId")



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CommentSubmit(
            profileUrl = "https://github.com/kmkim2689/flow-practice/assets/101035437/56eeb15a-f5e3-4b8e-8b5d-993d82d54c5a",
            onClickSubmit = { recipeId, content ->
                viewModel.postComment(recipeId, content)
            },
            recipeId = recipeId,
            placeHolder = stringResource(id = R.string.placeholder_comment)
        )
        
        CommentListContent(
            commentCount = commentCount,
            comments = comments,
            onClickReport = onClickReport,
            onClickBlock = onClickBlock,
            onClickEdit = onClickEdit,
            onClickDelete = onClickDelete,
            deviceHeight = viewModel.getDeviceHeight()
        )
    }
}