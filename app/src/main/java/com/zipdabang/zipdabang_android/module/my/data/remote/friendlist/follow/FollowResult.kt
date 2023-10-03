package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow

data class FollowResult(
    val currentPageElements: Int,
    val followingList: List<Following>,
    val isFirst: Boolean,
    val isLast: Boolean,
    val totalElements: Int,
    val totalPage: Int
)