package com.zipdabang.zipdabang_android.module.comment.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.component.CircleImage

@Composable
fun CommentSubmit(
    profileUrl: String,
    onClickSubmit: (Int, String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.size(32.dp)
        ) {
            CircleImage(imageUrl = profileUrl, contentDescription = "profile image")
        }

        Box(modifier = Modifier.fillMaxWidth()) {

        }
    }
}