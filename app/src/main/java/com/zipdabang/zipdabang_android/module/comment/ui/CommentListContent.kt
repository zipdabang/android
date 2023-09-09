package com.zipdabang.zipdabang_android.module.comment.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.zipdabang.zipdabang_android.module.comment.ui.components.CommentItem

@Composable
fun CommentListContent(
    commentCount: Int,
    comments: LazyPagingItems<RecipeCommentState>,
    onClickReport: (Int) -> Unit,
    onClickBlock: (Int) -> Unit,
    onClickEdit: (Int) -> Unit,
    onClickDelete: (Int) -> Unit,
    deviceHeight: Float
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