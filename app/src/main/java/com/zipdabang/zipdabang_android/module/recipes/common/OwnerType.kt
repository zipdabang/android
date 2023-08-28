package com.zipdabang.zipdabang_android.module.recipes.common

enum class OwnerType(val type: String, val title: String) {
    ALL(type = "all", title = "모든 사람들"),
    INFLUENCER(type = "influencer", title = "인플루언서"),
    USER(type = "common", title = "우리들")
}