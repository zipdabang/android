package com.zipdabang.zipdabang_android.module.drawer.ui.quit

enum class QuitReasons(val reason: String?, val requestString: String){
    Null(null,"null"),
    NOTHING_TO_BUY("사고싶은 물건이 없어요.","NOTHING_TO_BUY"),
    DISINTERESTED("앱을 이용하지 않아요.","DISINTERESTED"),
    UNCOMFORTABLE("앱 이용이 불편해요.","UNCOMFORTABLE"),
    NEW_REGISTER("새 계정을 만들고 싶어요.","NEW_REGISTER"),
    MET_RUDE_USER("비매너 유저를 만났어요.","MET_RUDE_USER"),
    OTHERS("기타","OTHERS")


}