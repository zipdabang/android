package com.zipdabang.zipdabang_android.module.my.ui.state

data class OtherInfo(
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val isLoading : Boolean = false,
    val isBlock : Boolean = false,
    val isFollowing : Boolean = false,
    val error : String = ""
)
