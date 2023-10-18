package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search

data class SearchFollowingDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: SearchFollowingResult?
)