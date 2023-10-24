package com.zipdabang.zipdabang_android.module.search.data.dto.common

enum class SearchSortList(val sortName : String, val sortRequest : String) {
    Latest("최신순","latest"),
    Popular("인기순","popular"),
    Follow("팔로우순","follow"),

}