package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import android.util.Patterns
import com.zipdabang.zipdabang_android.R
import java.util.regex.Pattern

class ValidateBirthdayUseCase {
    operator fun invoke(birthday : Int) : ValidationResult{
        //birthday가 6자리 미만인 경우
        if(birthday.toString().length < 6){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.signup_userinfo_birthday_lengthfail.toString()
            )
        }

        //birthday 형식에 맞지 않는 경우
        //좀 더 수정해야하나
        val birthdayPattern = "^\\d{6}$".toRegex()
        if(!birthdayPattern.matches(birthday.toString())){
            return ValidationResult(
                successful = false,
                errorMessage = R.string.signup_userinfo_birthday_notformed.toString()
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}