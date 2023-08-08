package com.zipdabang.zipdabang_android.module.login.data

import com.google.gson.annotations.SerializedName

data class AuthBody(
    @SerializedName("email") val email: String,
    @SerializedName("profileUrl") val profileUrl: String
)
