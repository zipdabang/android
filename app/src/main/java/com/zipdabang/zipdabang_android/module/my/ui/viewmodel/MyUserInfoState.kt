package com.zipdabang.zipdabang_android.module.my.ui.viewmodel

data class MyUserInfoState(
    val isLoading : Boolean = false,

    val profileUrl : String = "",
    val nickname : String ="닉네임",
    val name : String = "이름",
    val categoryList : List<String> = listOf("커피", "논카페인"),

    val error : String = ""
)

