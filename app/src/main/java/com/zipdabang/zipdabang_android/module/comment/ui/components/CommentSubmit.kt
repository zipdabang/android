package com.zipdabang.zipdabang_android.module.comment.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.CommentMgtFailureException
import com.zipdabang.zipdabang_android.module.comment.common.TextMode
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentState
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentViewModel
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun CommentSubmit(
    isLoading: Boolean,
    profileUrl: String,
    recipeId: Int,
    placeHolder: String,
    textMode: TextMode,
    onEdit: (Int, Int, String) -> Unit,
    commentId: Int = 0,
    currentText: String
) {

    Log.d("recipe comment", "current value inner textfield : $currentText")
    Log.d("recipe comment", "current commentId : $commentId")


    val viewModel = hiltViewModel<RecipeCommentViewModel>()

    val editState = viewModel.editResult.collectAsState()
    val deleteState = viewModel.deleteResult.collectAsState()

    if (editState.value.errorMessage != null
        || deleteState.value.errorMessage != null) {
        throw CommentMgtFailureException
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        Box(
            modifier = Modifier.size(40.dp)
        ) {
            CircleImage(imageUrl = profileUrl, contentDescription = "profile image")
        }

        Spacer(modifier = Modifier.width(8.dp))

        if (textMode == TextMode.EDIT && commentId != 0) {
            var text by rememberSaveable {
                mutableStateOf(currentText)
            }
            Log.d("recipe comment", "edit entered : $text")

            val isFulfilled by remember {
                derivedStateOf {
                    text.length in 1..100
                }
            }
            CommentEditTextField(
                isLoading = isLoading,
                recipeId = recipeId,
                value = text,
                onValueChange = { changedValue ->
                    if (changedValue.length in 0..100) {
                        text = changedValue
                    }
                },
                onSubmit = { recipeId, content ->
                    try {
                        viewModel.editComment(recipeId, commentId, content)
                        text = ""
                    } catch (e: CommentMgtFailureException) {
                        Log.d("comment submit", "edit failure")
                    } catch (e: Exception) {
                        Log.d("comment submit", "unknown failure : ${e.message}")
                    }

                },
                isFulfilled = isFulfilled,
                placeHolder = placeHolder,
                textMode = textMode,
                onEdit = onEdit,
                commentId = commentId
            )
        } else {
            var text by rememberSaveable {
                mutableStateOf("")
            }

            val isFulfilled by remember {
                derivedStateOf {
                    text.length in 1..100
                }
            }
            CommentTextField(
                isLoading = isLoading,
                recipeId = recipeId,
                value = text,
                onValueChange = { changedValue ->
                    if (changedValue.length in 0..100) {
                        text = changedValue
                    }
                },
                onSubmit = { recipeId, content ->
                    try {
                        viewModel.postComment(recipeId, content)
                        text = ""
                    } catch (e: CommentMgtFailureException) {
                        Log.d("comment submit", "edit failure")
                    } catch (e: Exception) {
                        Log.d("comment submit", "unknown failure : ${e.message}")
                    }
                },
                isFulfilled = isFulfilled,
                placeHolder = placeHolder
            )
        }
    }
}

/*
@Preview
@Composable
fun CommentSubmitPreview() {
    CommentSubmit(
        profileUrl = "https://github.com/kmkim2689/flow-practice/assets/101035437/56eeb15a-f5e3-4b8e-8b5d-993d82d54c5a",
        recipeId = 1,
        placeHolder = "Asdf",
        isLoading = true
    )
}*/
