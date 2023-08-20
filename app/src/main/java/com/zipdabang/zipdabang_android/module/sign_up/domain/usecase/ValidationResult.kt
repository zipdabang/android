package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

data class ValidationResult(
    val successful : Boolean,
    val errorMessage : String? = null,
)
