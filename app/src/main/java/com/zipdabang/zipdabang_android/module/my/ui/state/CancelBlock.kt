package com.zipdabang.zipdabang_android.module.my.ui.state

data class CancelBlock(
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val isLoading : Boolean = false,
    val isFollowing : Boolean = false,
    val error : String = ""
)

data class Block(
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val isLoading : Boolean = false,
    val error : String = ""
)