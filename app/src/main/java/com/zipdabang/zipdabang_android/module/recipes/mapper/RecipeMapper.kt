package com.zipdabang.zipdabang_android.module.recipes.mapper

import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.entity.recipe.RecipeAdeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeAllEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeBaristaEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeCoffeeEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeFruitEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeNonCaffeineEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeSmoothieEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeTeaEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeUserEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeWellBeingEntity
import com.zipdabang.zipdabang_android.entity.recipe.RecipeZipdabangEntity
import com.zipdabang.zipdabang_android.module.recipes.data.banner.RecipeBannerDto
import com.zipdabang.zipdabang_android.module.recipes.data.category.RecipeCategoryDto
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeDto
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeItemEntity
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto
import com.zipdabang.zipdabang_android.module.recipes.data.recipe_list.RecipeListDto
import com.zipdabang.zipdabang_android.module.recipes.domain.PreferenceToggle
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeBanner
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeCategoryItems
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipePreview

fun RecipePreviewItemsDto.toRecipePreview(): RecipePreview {
    return RecipePreview(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        recipeList = recipeResult?.recipeList
    )
}

fun PreferenceResultDto.toPreferenceToggle(): PreferenceToggle {
    return PreferenceToggle(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        recipeId = result?.recipeId
    )
}

fun RecipeCategoryDto.toRecipeCategoryItems(): RecipeCategoryItems {
    return RecipeCategoryItems(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        categoryList = recipeCategoryResult?.beverageCategoryList
    )
}

fun RecipeListDto.toRecipePreview(): RecipePreview {
    return RecipePreview(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        recipeList = result?.recipeList
    )
}

fun RecipeBannerDto.toRecipeBanner(): RecipeBanner {
    return RecipeBanner(
        code = code,
        isSuccessful = isSuccess,
        message = message,
        recipeBanners = result.bannerList
    )
}

fun RecipeItem.toRecipeItemEntity(): RecipeItemEntity {
    return RecipeItemEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItemEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItem.toRecipeCoffeeEntity(): RecipeCoffeeEntity {
    return RecipeCoffeeEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeCoffeeEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItem.toRecipeNonCaffeineEntity(): RecipeNonCaffeineEntity {
    return RecipeNonCaffeineEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeNonCaffeineEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItem.toRecipeTeaEntity(): RecipeTeaEntity {
    return RecipeTeaEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeTeaEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItem.toRecipeAdeEntity(): RecipeAdeEntity {
    return RecipeAdeEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeAdeEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItem.toRecipeSmoothieEntity(): RecipeSmoothieEntity {
    return RecipeSmoothieEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeSmoothieEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItem.toRecipeFruitEntity(): RecipeFruitEntity {
    return RecipeFruitEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeFruitEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItem.toRecipeWellBeingEntity(): RecipeWellBeingEntity {
    return RecipeWellBeingEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeWellBeingEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItem.toRecipeAllEntity(): RecipeAllEntity {
    return RecipeAllEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeAllEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItem.toRecipeZipdabangEntity(): RecipeZipdabangEntity {
    return RecipeZipdabangEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeZipdabangEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItem.toRecipeBaristaEntity(): RecipeBaristaEntity {
    return RecipeBaristaEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeBaristaEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeItem.toRecipeUserEntity(): RecipeUserEntity {
    return RecipeUserEntity(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun RecipeUserEntity.toRecipeItem(): RecipeItem {
    return RecipeItem(
        categoryId = categoryId,
        comments = comments,
        createdAt = createdAt,
        isLiked = isLiked,
        isScrapped = isScrapped,
        likes = likes,
        nickname = nickname,
        recipeId = recipeId,
        recipeName = recipeName,
        scraps = scraps,
        thumbnailUrl = thumbnailUrl
    )
}

fun ResponseBody<HotRecipeDto>.toHotRecipes(): ResponseBody<List<HotRecipeItem>> {
    return ResponseBody(
        isSuccess = isSuccess,
        code = code,
        message = message,
        result = result?.recipeList ?: emptyList()
    )
}