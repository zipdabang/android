package com.zipdabang.zipdabang_android.core.data_store.proto

import kotlinx.serialization.Serializable

// kotlin serialization plugin에게 해당 객체를 JSON으로 변환 / 혹은 JSON을 해당 객체로 변환하도록 하는 annotation
// 만약 data class안에 다른 data class를 사용한다면, 내부에서 사용되는 data class에 대해서도 @Serializable annotation 활용해야 한다.
@Serializable
data class Token(
    var accessToken: String?,
    var refreshToken: String?,
    var platformToken: String?,
    var platformStatus: CurrentPlatform,
    var fcmToken: String?,
    // 새로운 필드 추가 시, 기본값을 설정해 주어야 런타임 에러를 피할 수 있음
    var deviceNumber: String? = null
)

// 선택의 폭을 제한
enum class CurrentPlatform {
    NONE, KAKAO, GOOGLE, TEMP
}

