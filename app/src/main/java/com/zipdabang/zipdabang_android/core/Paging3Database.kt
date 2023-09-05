package com.zipdabang.zipdabang_android.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zipdabang.zipdabang_android.core.remotekey.dao.RemoteKeyDao
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.Category_Product
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.market.domain.dao.MarketCategoryDao
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeListDao


@Database(entities = [Category_Product::class, RemoteKeys::class, RecipeItem::class], version = 2, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class Paging3Database : RoomDatabase() {
    abstract fun CategoryDao() : MarketCategoryDao
    abstract fun RemoteKeyDao() : RemoteKeyDao
    abstract fun recipeListDao(): RecipeListDao
}