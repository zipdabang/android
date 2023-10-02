package com.zipdabang.zipdabang_android.module.comment.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.module.comment.common.TextMode
import com.zipdabang.zipdabang_android.module.comment.ui.components.CommentItem
import com.zipdabang.zipdabang_android.module.comment.ui.components.CommentSubmit

@Composable
fun CommentListContent(
    commentCount: Int,
    comments: LazyPagingItems<RecipeCommentState>,
    onClickReport: (Int, Int, Int) -> Unit,
    onClickBlock: (Int) -> Unit,
    onClickEdit: (Int, Int, String) -> Unit,
    onClickDelete: (Int, Int) -> Unit,
    postResult: PostCommentState,
    recipeId: Int,
    showCommentReport: (Int, Int, Int, Int) -> Unit,
    showCommentBlock: (Int) -> Unit
) {

    var text by rememberSaveable {
        mutableStateOf("")
    }

    var textMode by rememberSaveable {
        mutableStateOf(TextMode.POST)
    }

    var currentCommentId by rememberSaveable {
        mutableStateOf(0)
    }

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
                placeHolder = stringResource(id = R.string.placeholder_comment),
                currentText = text,
                textMode = textMode,
                onEdit = { recipeId, commentId, newContent ->
                    onClickEdit(recipeId, commentId, newContent)
                    text = ""
                    textMode = TextMode.POST
                },
                commentId = currentCommentId
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
                    recipeId = recipeId,
                    commentItem = commentItem,
                    onClickReport = onClickReport,
                    onClickBlock = onClickBlock,
                    onClickEdit = { commentId, currentContent ->
                        Log.d("recipe comment", currentContent)
                        text = currentContent
                        textMode = TextMode.EDIT
                        currentCommentId = commentId
                    },
                    onClickDelete = onClickDelete,
                    showCommentReport = showCommentReport,
                    showCommentBlock = showCommentBlock
                )
            }
        }
    }
}