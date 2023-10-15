package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search

data class SearchFollowersDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: SearchFollowerResult
)