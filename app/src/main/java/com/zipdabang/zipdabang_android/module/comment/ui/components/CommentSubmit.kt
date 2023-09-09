package com.zipdabang.zipdabang_android.module.comment.ui.components

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
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentState
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun CommentSubmit(
    profileUrl: String,
    onClickSubmit: (Int, PostCommentContent) -> Unit,
    recipeId: Int,
    placeHolder: String
) {

    var text by rememberSaveable {
        mutableStateOf("")
    }
    
    val isFulfilled by remember {
        derivedStateOf {
            text.length in 1..100
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
    ) {
        Box(
            modifier = Modifier.size(40.dp)
        ) {
            CircleImage(imageUrl = profileUrl, contentDescription = "profile image")
        }

        Spacer(modifier = Modifier.width(8.dp))

        CommentTextField(
            recipeId = recipeId,
            value = text,
            onValueChange = { changedValue ->
                if (changedValue.length in 0..100) {
                    text = changedValue
                }
            },
            onSubmit = onClickSubmit,
            isFulfilled = isFulfilled,
            placeHolder = placeHolder
        )
    }
}

@Preview
@Composable
fun CommentSubmitPreview() {
    CommentSubmit(
        profileUrl = "https://github.com/kmkim2689/flow-practice/assets/101035437/56eeb15a-f5e3-4b8e-8b5d-993d82d54c5a",
        onClickSubmit = { id, content -> },
        recipeId = 1,
        placeHolder = "Asdf"
    )
}