package com.zipdabang.zipdabang_android.module.my.util

import com.zipdabang.zipdabang_android.module.my.data.remote.signout.SignOutResponseDto
import com.zipdabang.zipdabang_android.module.my.domain.SignOutResponse

fun SignOutResponseDto.toSignOutResponse(): SignOutResponse {
    return SignOutResponse(
        code = code,
        isSuccess = isSuccess,
        message = message,
        isSignOutSuccessful = result?.calledAt != null
    )
}