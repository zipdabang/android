package com.zipdabang.zipdabang_android.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zipdabang.zipdabang_android.core.remotekey.dao.RemoteKeyDao
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.Category_Product
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentDao
import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentEntity
import com.zipdabang.zipdabang_android.module.drawer.data.ReportDao
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.Inquery
import com.zipdabang.zipdabang_android.module.drawer.data.remote.reporterror.InqueryDB
import com.zipdabang.zipdabang_android.module.market.domain.dao.MarketCategoryDao
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.FollowDao
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.Following
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.FollowInfo
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.FollowInfoDB
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.SearchFollowDao
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.Follower
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.FollowingDao
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search.FollowerInfoDB
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search.SearchFollowerDao
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeItemEntity
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeListDao
import com.zipdabang.zipdabang_android.module.search.data.dto.common.SearchRecipe
import com.zipdabang.zipdabang_android.module.search.data.SearchDao

@Database(
    entities = [
        Category_Product::class, RemoteKeys::class,
        RecipeItemEntity::class, SearchRecipe::class,
        RecipeCommentEntity::class , Following :: class,
        Follower :: class, InqueryDB :: class,
        FollowerInfoDB :: class, FollowInfoDB::class
    ],
    version = 14, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class Paging3Database : RoomDatabase() {
    abstract fun CategoryDao() : MarketCategoryDao
    abstract fun RemoteKeyDao() : RemoteKeyDao
    abstract fun recipeListDao(): RecipeListDao
    abstract fun SearchRecipeDao() : SearchDao
    abstract fun recipeCommentDao(): RecipeCommentDao
    abstract fun followDao() : FollowDao
    abstract fun followingDao() : FollowingDao
    abstract fun reportDao() : ReportDao
    abstract fun searchFollowDao() : SearchFollowDao
    abstract fun searchFollowerDao() : SearchFollowerDao


}