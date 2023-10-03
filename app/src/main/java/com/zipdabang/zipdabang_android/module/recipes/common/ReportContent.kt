package com.zipdabang.zipdabang_android.module.recipes.common

enum class ReportContent(val id: Int, val content: String) {
    PROMOTIONAL(1, "영리목적/홍보성"),
    VIOLATING_COPYRIGHT(2, "저작권침해"),
    PORNOGRAPHIC(3, "음란성/선정성"),
    INSULTING(4, "욕설/인신공격"),
    EXPOSING_PERSONAL_DATA(5, "개인정보노출"),
    SPAMMING(6, "같은내용 반복게시"),
    ETC(7, "기타");

    companion object {
        val contents = listOf(
            PROMOTIONAL,
            VIOLATING_COPYRIGHT,
            PORNOGRAPHIC,
            INSULTING,
            EXPOSING_PERSONAL_DATA,
            SPAMMING,
            ETC
        )
    };
}