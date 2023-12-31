package com.zipdabang.zipdabang_android.module.comment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.Resource
import com.zipdabang.zipdabang_android.module.comment.common.TextMode
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentTextField(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    recipeId: Int,
    value: String,
    onValueChange: (String) -> Unit,
    onSubmit: (Int, String) -> Unit,
    isFulfilled: Boolean,
    placeHolder: String,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F6F6)),
        value = value,
        onValueChange = onValueChange,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = ZipdabangandroidTheme.Colors.Strawberry,
            focusedBorderColor = Color(0xFFE2E2E2),
            unfocusedBorderColor = Color(0xFFE2E2E2),
        ),
        textStyle = TextStyle(
            color = ZipdabangandroidTheme.Colors.Typo,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium))
        ),
        placeholder = {
            Text(
                text = placeHolder,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_light)),
                color = Color(0x80262D31)
            )
        },
        suffix = {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(26.dp),
                    color = ZipdabangandroidTheme.Colors.Choco
                )
            } else {
                CommentSubmitButton(
                    recipeId = recipeId,
                    commentContent = value,
                    onClick = onSubmit,
                    isFulfilled = isFulfilled
                )
            }
        }
    )
}

/*
@Preview
@Composable
fun CommentTextFieldPreview() {
    CommentTextField(
        recipeId = 1,
        value = "asdfasdfe",
        onValueChange = { content -> },
        onSubmit = { id, content -> },
        isFulfilled = true,
        placeHolder = "adsf",
        isLoading = true
    )
}*/
