package com.zipdabang.zipdabang_android.common

enum class ResponseCode(val responseResult: ResponseResult, val code: Int, val message: String) {
    OAUTH_SIGN_UP_NEEDED(ResponseResult.OK, 2010, "로그인 되었습니다."),
    OAUTH_SIGN_IN_SUCCESSFUL(ResponseResult.OK, 2011, "회원가입이 필요합니다."),
    RESPONSE_DEFAULT(ResponseResult.OK, 2000, "데이터를 성공적으로 불러왔습니다."),
    RESPONSE_NO_DATA(ResponseResult.OK, 2100, "통신에 성공하였으나 데이터가 없습니다."),
    UNAUTHORIZED_TOKEN_UNUSUAL(ResponseResult.ERROR, 4006, "부적절한 토큰입니다. 올바른 토큰을 전송하세요"),
    UNAUTHORIZED_TOKEN_EXPIRED(ResponseResult.ERROR, 4007, "액세스 토큰이 만료되었습니다."),
    UNAUTHORIZED_TOKEN_NOT_EXISTS(ResponseResult.ERROR, 4010, "토큰이 없습니다."),
    BAD_REQUEST_USER_NOT_EXISTS(ResponseResult.ERROR, 4013, "사용자가 없습니다. 서버 개발자에게 문의하세요."),
    BAD_REQUEST_RECIPE_NOT_EXISTS(ResponseResult.ERROR, 4101, "존재하지 않는 레시피 아이디입니다."),
    SERVER_ERROR(ResponseResult.ERROR, 5000, "서버에러가 발생하였습니다. 서버 개발자에게 문의하세요.");

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
