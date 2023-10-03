package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following

data class FollowingResult(
    val currentPageElements: Int,
    val followerList: List<Follower>,
    val isFirst: Boolean,
    val isLast: Boolean,
    val totalElements: Int,
    val totalPage: Int
)