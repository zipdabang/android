package com.zipdabang.zipdabang_android.module.drawer.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.Inquery
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.InqueryDB
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe

@Dao
interface ReportDao {


        @Query("SELECT * FROM report_table ORDER BY `index`")
        fun getAllItem() : PagingSource<Int, InqueryDB>

        @Query("SELECT id FROM report_table")
        fun getAllId() : Int

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun addItems(items : List<InqueryDB>)

        @Query("DELETE FROM REPORT_TABLE")
        suspend fun deleteItems()




}