package com.zipdabang.zipdabang_android.common

import android.util.Log
import kotlinx.serialization.Serializable
import okhttp3.ResponseBody
import org.json.JSONObject

@Serializable
data class ResponseBody<T: Any?>(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: T?
)

fun ResponseBody.getErrorCode(): Int? {
    return try {
        val errorObject = JSONObject(this.string())
        errorObject.getInt("code")
    } catch (e: Exception) {
        Log.e("something wrong happened while parsing errorBody", "$e")
        null
    }
}