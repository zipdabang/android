package com.zipdabang.zipdabang_android.core.storage.recipe

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zipdabang.zipdabang_android.core.ListConverter
import com.zipdabang.zipdabang_android.entity.recipe.RecipeAdeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeAllEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeBaristaEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeCoffeeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeCommentsEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeFruitEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeNonCaffeineEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeSmoothieEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeTeaEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeUserEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeWellBeingEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeZipdabangEntity
import com.zipdabang.zipdabang_android.entity.recipe.RemoteKeysEntity

@Database(
    entities = [
        RecipeCoffeeEntity::class,
        RecipeNonCaffeineEntity::class,
        RecipeTeaEntity::class,
        RecipeAdeEntity::class,
        RecipeSmoothieEntity::class,
        RecipeFruitEntity::class,
        RecipeWellBeingEntity::class,
        RecipeAllEntity::class,
        RecipeZipdabangEntity::class,
        RecipeBaristaEntity::class,
        RecipeUserEntity::class,
        RecipeCommentsEntity::class,
        RemoteKeysEntity::class
    ], version = 1
)
@TypeConverters(ListConverter::class)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipeCoffeeDao(): RecipeCoffeeDao
    abstract fun recipeNonCaffeineDao(): RecipeNonCaffeineDao
    abstract fun recipeTeaDao(): RecipeTeaDao
    abstract fun recipeAdeDao(): RecipeAdeDao
    abstract fun recipeSmoothieDao(): RecipeSmoothieDao
    abstract fun recipeFruitDao(): RecipeFruitDao
    abstract fun recipeWellBeingDao(): RecipeWellBeingDao
    abstract fun recipeAllDao(): RecipeAllDao
    abstract fun recipeZipdabangDao(): RecipeZipdabangDao
    abstract fun recipeBaristaDao(): RecipeBaristaDao
    abstract fun recipeUserDao(): RecipeUserDao
    abstract fun recipeCommentsDao(): RecipeCommentsDao
    abstract fun recipeRemoteKeyDao(): RecipeRemoteKeyDao
}