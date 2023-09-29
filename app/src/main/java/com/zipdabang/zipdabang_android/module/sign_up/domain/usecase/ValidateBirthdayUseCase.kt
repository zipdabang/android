package com.zipdabang.zipdabang_android.module.sign_up.domain.usecase

import android.util.Patterns
import com.zipdabang.zipdabang_android.R
import java.util.Calendar
import java.util.regex.Pattern
import javax.inject.Inject
import android.content.Context

class ValidateBirthdayUseCase @Inject constructor(

){
    operator fun invoke(birthday : String) : ValidationResult{

        //birthday가 6자리 미만인 경우
        if(birthday.length < 6){
            return ValidationResult(
                successful = false,
                errorMessage ="생년월일 6자리를 입력해주세요"
            )
        }

        //15세 미만일 경우
        val currentDate = Calendar.getInstance()
        val currentYear = currentDate.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH) + 1

        val birthYear = birthday.substring(0, 2).toInt()
        val birthMonth = birthday.substring(2, 4).toInt()
        val birthDay = birthday.substring(4,6).toInt()
        var birthYearReal : Int
        if(birthYear <= (currentYear-2000)){
            birthYearReal = birthYear + 2000
        } else {
            birthYearReal = birthYear + 1900
        }
        val age = currentYear - birthYearReal - if(currentMonth < birthMonth) 1 else 0

        if (age < 15) {
            return ValidationResult(
                successful = false,
                errorMessage = "만 15세 미만은 가입이 불가해요"
            )
        }

        // 월을 잘못 썼을 경우
        if(birthMonth > 12){
            return ValidationResult(
                successful = false,
                errorMessage = "생년월일 형식에 맞지 않습니다"
            )
        }

        // 일을 잘못 썼을 경우
        if(birthDay > 31){
            return ValidationResult(
                successful = false,
                errorMessage = "생년월일 형식에 맞지 않습니다"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}