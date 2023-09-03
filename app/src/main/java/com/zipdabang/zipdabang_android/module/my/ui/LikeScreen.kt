package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp

@Composable
fun LikeScreen(
    onClickBack : ()->Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
                centerText = stringResource(id = R.string.my_like)
            )
        },
        containerColor = Color.White,
        contentColor = Color.White,
    ){
        Surface(
            modifier = Modifier.padding(it)
        ){

        }
    }
}

@Preview
@Composable
fun PreviewLikeScreen() {
    LikeScreen(onClickBack = {})
}