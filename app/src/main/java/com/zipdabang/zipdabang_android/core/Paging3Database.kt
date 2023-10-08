package com.zipdabang.zipdabang_android.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zipdabang.zipdabang_android.core.remotekey.dao.RemoteKeyDao
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.Category_Product
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentDao
import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentEntity
import com.zipdabang.zipdabang_android.module.market.domain.dao.MarketCategoryDao
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDao
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.Following
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.Follower
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDao
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeItemEntity
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeListDao
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import com.zipdabang.zipdabang_android.module.search.domain.SearchDao

@Database(
    entities = [
        Category_Product::class, RemoteKeys::class,
        RecipeItemEntity::class, SearchRecipe::class,
        RecipeCommentEntity::class , Following :: class,
        Follower :: class
    ],
    version = 10, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class Paging3Database : RoomDatabase() {
    abstract fun CategoryDao() : MarketCategoryDao
    abstract fun RemoteKeyDao() : RemoteKeyDao
    abstract fun recipeListDao(): RecipeListDao
    abstract fun SearchRecipeDao() : SearchDao
    abstract fun recipeCommentDao(): RecipeCommentDao
    abstract fun followDao() : FollowDao
    abstract fun followingDao() : FollowingDao

}