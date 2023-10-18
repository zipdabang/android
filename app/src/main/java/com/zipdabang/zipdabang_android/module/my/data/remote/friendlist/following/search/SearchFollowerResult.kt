package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search

data class SearchFollowerResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val memberSimpleDtoList: List<FollowerInfo>,
    val totalElements: Int,
    val totalPage: Int
)