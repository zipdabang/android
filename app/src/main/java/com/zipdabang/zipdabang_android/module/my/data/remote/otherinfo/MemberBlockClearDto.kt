package com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo

data class MemberBlockClearDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: MemberBlockResult

)