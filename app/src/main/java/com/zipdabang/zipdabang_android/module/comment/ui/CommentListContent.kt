package com.zipdabang.zipdabang_android.module.comment.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.module.comment.ui.components.CommentItem
import com.zipdabang.zipdabang_android.module.comment.ui.components.CommentSubmit

@Composable
fun CommentListContent(
    commentCount: Int,
    comments: LazyPagingItems<RecipeCommentState>,
    onClickReport: (Int) -> Unit,
    onClickBlock: (Int) -> Unit,
    onClickEdit: (Int) -> Unit,
    onClickDelete: (Int) -> Unit,
    postResult: PostCommentState,
    recipeId: Int
) {


    LaunchedEffect(key1 = comments.loadState) {
        if (comments.loadState.refresh is LoadState.Loading) {
            /*            items(count = 10) {
                            RecipeCardLoading()
                        }*/
            Log.d("commentlist - content", "loading...")
        } else if (comments.loadState.refresh is LoadState.Error) {
            Log.d("commentlist - content", (comments.loadState.refresh as LoadState.Error).error.message ?: "unexpected error")
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            //.height((deviceHeight / 2).dp)
    ) {

        item {
            CommentSubmit(
                isLoading = postResult.isLoading,
                profileUrl = "https://github.com/kmkim2689/flow-practice/assets/101035437/56eeb15a-f5e3-4b8e-8b5d-993d82d54c5a",
                recipeId = recipeId,
                placeHolder = stringResource(id = R.string.placeholder_comment)
            )
        }

        items(
            count = comments.itemCount,
            key = comments.itemKey { comment ->
                comment.commentId
            },
            contentType = comments.itemContentType { "comment" }
        ) { idx ->
            val commentItem = comments[idx]

            if (commentItem != null) {
                CommentItem(
                    commentItem = commentItem,
                    onClickReport = onClickReport,
                    onClickBlock = onClickBlock,
                    onClickEdit = onClickEdit,
                    onClickDelete = onClickDelete
                )
            }
        }
    }
}