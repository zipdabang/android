package com.zipdabang.zipdabang_android.module.comment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun CommentSubmitButton(
    modifier: Modifier = Modifier,
    isFulfilled: Boolean = false,
    recipeId: Int,
    commentContent: PostCommentContent,
    onClick: (Int, PostCommentContent) -> Unit
) {
    Column(
        modifier = modifier
            .width(50.dp)
            .height(26.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(50)
            )
            .clickable(enabled = isFulfilled) {
                if (isFulfilled) {
                    onClick(recipeId, commentContent)
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.btn_register_comment),
            color = if (isFulfilled) Color(0xFFE7AC98) else Color(0x80262D31),
            fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
fun CommentButtonPreview() {
    CommentSubmitButton(
        recipeId = 1,
        commentContent = PostCommentContent(""),
        onClick = { id, content -> }
    )
}