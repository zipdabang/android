package com.zipdabang.zipdabang_android.common

sealed class Resource<T>(val data: T? = null, val code: Int? = null, val message: String? = null) {
    class Success<T>(data: T, code: Int, message: String)
        : Resource<T>(data = data, code = code, message = message)

    class Error<T>(message: String, data: T? = null)
        : Resource<T>(message = message, data = data)

    class Loading<T>(data: T? = null): Resource<T>(data)
}

sealed class LoginResource<T>(val data: T? = null, val message: String? = null) {
    class LoginSuccess<T>(data: T): LoginResource<T>(data = data)

    class LoginError<T>(message: String)
        : LoginResource<T>(message = message)

    class LoginLoading<T>(isLoading: Boolean): LoginResource<T>(null)
}