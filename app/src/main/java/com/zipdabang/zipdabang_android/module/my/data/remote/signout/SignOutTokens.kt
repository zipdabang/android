package com.zipdabang.zipdabang_android.module.my.data.remote.signout

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class SignOutTokens(
    @SerializedName("fcmToken") val fcmToken: String,
    @SerializedName("serialNumber") val serialNumber: String
)
