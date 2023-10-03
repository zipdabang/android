package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.theme.DialogBackground

@Composable
fun Separator() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .background(DialogBackground)
    )
}