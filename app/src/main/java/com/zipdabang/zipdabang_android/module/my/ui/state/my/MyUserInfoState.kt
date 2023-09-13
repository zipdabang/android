package com.zipdabang.zipdabang_android.module.my.ui.state.my

import androidx.compose.ui.res.stringResource
import com.zipdabang.zipdabang_android.R

data class MyUserInfoState(
    val isLoading : Boolean = false,

    val profileUrl : String = "",
    val nickname : String ="비니",
    val name : String = "집다방",
    val categoryList : List<String> = listOf("커피", "스무디"),

    val error : String = ""
)

