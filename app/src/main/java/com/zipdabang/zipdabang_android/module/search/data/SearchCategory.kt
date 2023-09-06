package com.zipdabang.zipdabang_android.module.search.data

enum class SearchCategory(val categoryName : String,val categoryId : Int) {
    Coffee("커피",1),
    NonCaffeine("카페인",2),
    Tea("차(티)",3),
    Ade("에이드",4),
    Smoothie("스무디",5),
    FruitBeverage("과일 음료",6),
    HealthBeverage("건강 음료",7)
}