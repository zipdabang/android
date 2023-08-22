package com.zipdabang.zipdabang_android.common

enum class ResponseCode(val responseResult: ResponseResult, val code: Int, val message: String) {
    OAUTH_SIGN_UP_NEEDED(ResponseResult.OK, 2010, "로그인 되었습니다."),
    OAUTH_SIGN_IN_SUCCESSFUL(ResponseResult.OK, 2011, "회원가입이 필요합니다.");

    companion object {
        fun getMessageByCode(code: Int): String {
            return values().find { it.code == code }!!.message
        }
    }


}

enum class ResponseResult {
    OK,
    ERROR
}
