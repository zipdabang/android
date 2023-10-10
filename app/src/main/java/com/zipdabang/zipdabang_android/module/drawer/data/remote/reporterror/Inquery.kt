package com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants


@Entity(tableName = Constants.REPORT_TABLE)
data class InqueryDB(
    val index : Int,
    val createdAt: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String
)

data class Inquery(
    val createdAt: String,
    val id: Int,
    val title: String
)