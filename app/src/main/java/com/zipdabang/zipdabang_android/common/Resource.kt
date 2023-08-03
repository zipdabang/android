package com.zipdabang.zipdabang_android.common

sealed class Resource<T>(val data: T? = null, val code: Int? = null, val message: String? = null) {
    class Success<T>(data: T, code: Int, message: String)
        : Resource<T>(data = data, code = code, message = message)

    class Error<T>(code: Int, message: String)
        : Resource<T>(code = code, message = message)

    class Loading<T>(isLoading: Boolean): Resource<T>(null)
}

sealed class LoginResource<T>(val data: T? = null, val message: String? = null) {
    class LoginSuccess<T>(data: T): LoginResource<T>(data = data)

    class LoginError<T>(message: String)
        : LoginResource<T>(message = message)

    class LoginLoading<T>(isLoading: Boolean): LoginResource<T>(null)
}