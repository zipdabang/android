package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search

data class SearchFollowingResult(
    val currentPageElements: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    val memberSimpleDtoList: List<FollowInfo>,
    val totalElements: Int,
    val totalPage: Int
)