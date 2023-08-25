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

sealed class MarketResource<T>(val data: T? = null, val message: String? = null) {
    class MarketSuccess<T>(data: T): MarketResource<T>(data = data)

    class MarketError<T>(message: String)
        : MarketResource<T>(message = message)

    class MarketLoading<T>(isLoading: Boolean): MarketResource<T>(null)
}

sealed class HomeResource<T>(val data: T? = null, val message: String? = null) {
    class HomeSuccess<T>(data: T): HomeResource<T>(data = data)

    class HomeError<T>(message: String)
        : HomeResource<T>(message = message)

    class HomeLoading<T>(isLoading: Boolean): HomeResource<T>(null)
}