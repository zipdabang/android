package com.zipdabang.zipdabang_android.common

enum class ResponseCode(val responseResult: ResponseResult, val code: Int, val message: String) {
    RESPONSE_DEFAULT(ResponseResult.OK, 2000, "데이터를 성공적으로 불러왔습니다."),
    OAUTH_SIGN_IN_SUCCESSFUL(ResponseResult.OK, 2050, "로그인 되었습니다."),
    OAUTH_SIGN_UP_NEEDED(ResponseResult.OK, 2051, "회원가입이 필요합니다."),
    OAUTH_SIGN_UP_NICKNAME_EXISTS(ResponseResult.OK, 2052, "닉네임이 이미 존재합니다."),
    OAUTH_SIGN_UP_PHONE_EXISTS(ResponseResult.OK, 2054, "전화번호가 이미 존재합니다."),
    OAUTH_SIGN_UP_NICKNAME_AVAILABLE(ResponseResult.OK, 2053, "닉네임이 사용 가능합니다."),
    OAUTH_REMEMBER_ME_SUCCESSFUL(ResponseResult.OK, 2055, "자동로그인이 성공적으로 이뤄졌습니다."),
    OAUTH_REMEMBER_ME_FAILURE(ResponseResult.OK, 2056, "로그인이 필요합니다."),
    RESPONSE_NO_DATA(ResponseResult.OK, 2100, "통신에 성공하였으나 데이터가 없습니다."),
    UNAUTHORIZED_TOKEN_UNUSUAL(ResponseResult.ERROR, 4003, "부적절한 토큰입니다. 올바른 토큰을 전송하세요"),
    UNAUTHORIZED_ACCESS_EXPIRED(ResponseResult.ERROR, 4005, "액세스 토큰이 만료되었습니다."),
    UNAUTHORIZED_REFRESH_EXPIRED(ResponseResult.ERROR, 4006, "리프레시 토큰이 만료되었습니다."),
    UNAUTHORIZED_TOKEN_NOT_EXISTS(ResponseResult.ERROR, 4008, "토큰이 없습니다."),
    BAD_REQUEST_REFRESH_NOT_EXISTS(ResponseResult.ERROR, 4050, "리프레시 토큰이 서버로 넘어오지 않았습니다."),
    BAD_REQUEST_USER_NOT_EXISTS(ResponseResult.ERROR, 4052, "사용자가 없습니다. 서버 개발자에게 문의하세요."),
    BAD_REQUEST_CATEGORY_ID_NOT_EXISTS(ResponseResult.ERROR, 4053, "선호하는 음료 카테고리 id가 이상합니다."),
    BAD_REQUEST_INDEX_MINUS(ResponseResult.ERROR, 4054, "인덱스는 1 이상이어야 합니다."),
    BAD_REQUEST_INDEX_EXCEEDED(ResponseResult.ERROR, 4055, "페이지 인덱스 범위를 초과했습니다."),
    BAD_REQUEST_PAGE_INDEX_ERROR(ResponseResult.ERROR, 4055, "페이지 인덱스 범위 초과했습니다."),
    BAD_REQUEST_PHONE_AUTH_NOT_REQUEST(ResponseResult.ERROR, 4056, "전화번호 인증요청을 하지 않았습니다. 인증요청을 해주세요."),
    BAD_REQUEST_PHONE_AUTH_NOT_CORRECT(ResponseResult.ERROR, 4057, "인증번호가 옳지 않습니다."),
    BAD_REQUEST_PHONE_AUTH_TIMEOUT(ResponseResult.ERROR, 4058, "인증시간 5분이 지났습니다."),
    BAD_REQUEST_MY_HAVE_TO_LOGIN(ResponseResult.ERROR, 4059, "로그인 후 조회 가능합니다."),
    BAD_REQUEST_USER_ALREADY_BLOCKED(ResponseResult.ERROR, 4062, "이미 차단된 사용자입니다."),
    BAD_REQUEST_BLOCKED_ME(ResponseResult.ERROR, 4063, "자신을 차단할 수 없습니다."),
    BAD_REQUEST_RECIPEWRITE_NOT_FORMED(ResponseResult.ERROR, 4100, "누락된 부분이 있습니다."),
    BAD_REQUEST_RECIPE_NOT_EXISTS(ResponseResult.ERROR, 4101, "존재하지 않는 레시피 아이디입니다."),
    BAD_REQUEST_RECIPE_BANNED(ResponseResult.ERROR, 4102, "차단한 사용자의 레시피입니다."),
    BAD_REQUEST_OWNER_TYPE(ResponseResult.ERROR, 4103, "레시피 작성자 타입이 잘못되었습니다."),
    BAD_REQUEST_COMMENT_NOT_EXISTS(ResponseResult.ERROR, 4107, "해당 아이디를 가진 댓글이 존재하지 않습니다."),
    BAD_REQUEST_COMMENT_NOT_OWNER(ResponseResult.ERROR, 4108, "해당 댓글을 작성한 사용자가 아닙니다."),
    BAD_REQUEST_RECIPEWRITE_TEMPID_NOT_EXISTS(ResponseResult.ERROR, 4111, "해당 임시저장 아이디가 존재하지 않습니다."),
    SERVER_ERROR(ResponseResult.ERROR, 5000, "서버에러가 발생하였습니다. 서버 개발자에게 문의하세요.");




    companion object {
        fun getMessageByCode(code: Int): String {
            return values().find { it.code == code }?.message ?: "code not exists. unexpected error"
        }
    }
}

enum class ResponseResult {
    OK,
    ERROR
}