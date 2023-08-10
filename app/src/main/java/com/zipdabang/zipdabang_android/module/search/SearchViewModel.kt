package com.zipdabang.zipdabang_android.module.search
import android.util.Log
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {



    fun log(value : String){
        var ss = value

        Log.d("search", ss.toString())
    }


}