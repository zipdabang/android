package com.zipdabang.zipdabang_android.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zipdabang.zipdabang_android.core.dao.RemoteKeyDao
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.Category_Product
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.RemoteKeys
import com.zipdabang.zipdabang_android.module.market.domain.dao.MarketCategoryDao


@Database(entities = [Category_Product::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class Paging3Database : RoomDatabase() {
    abstract fun CategoryDao() : MarketCategoryDao
    abstract fun RemoteKeyDao() : RemoteKeyDao
}