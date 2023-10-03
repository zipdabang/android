package com.zipdabang.zipdabang_android.module.recipes.common

enum class OwnerType(
    val type: String,
    val title: String,
    val subTitle: String
) {
    ALL(
        type = "all",
        title = "집다방의\n모든 레시피",
        subTitle = "맛있는 것만 골라 골라온",
    ),
    INFLUENCER(
        type = "influencer",
        title = "집다방 바리스타의\n연구 레시피",
        subTitle = "집다방이 심사숙고해 선정한"
    ),
    USER(
        type = "common",
        title = "우리들의\n자체 제작 레시피",
        subTitle = "언제든지 만들어 먹을 수 있는"
    )
}