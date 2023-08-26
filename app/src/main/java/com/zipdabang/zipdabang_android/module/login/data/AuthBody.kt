package com.zipdabang.zipdabang_android.module.login.data

import com.google.gson.annotations.SerializedName

data class AuthBody(
    @SerializedName("email") val email: String,
    @SerializedName("fcmToken") val fcmToken: String,
    @SerializedName("serialNumber") val serialNumber: String
)
