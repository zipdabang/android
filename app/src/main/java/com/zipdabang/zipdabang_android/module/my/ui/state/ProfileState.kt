package com.zipdabang.zipdabang_android.module.my.ui.state

import com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo.OtherPreferCategory

data class ProfileState(
   val caption : String? = null,
   val preferCategory: List<OtherPreferCategory> = emptyList()
)
