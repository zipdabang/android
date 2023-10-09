package com.zipdabang.zipdabang_android.module.recipes.data.common

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.zipdabang.zipdabang_android.common.Constants.ITEMS_PER_PAGE
import com.zipdabang.zipdabang_android.common.ResponseBody
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.storage.recipe.RecipeDatabase
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
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeDto
import com.zipdabang.zipdabang_android.module.recipes.data.local.RecipeItemEntity
import com.zipdabang.zipdabang_android.module.recipes.data.preference.PreferenceResultDto
import com.zipdabang.zipdabang_android.module.recipes.data.preview.RecipePreviewItemsDto
import com.zipdabang.zipdabang_android.module.recipes.data.recipe_list.RecipeListDto
import com.zipdabang.zipdabang_android.module.recipes.domain.RecipeListRepository
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.AdeRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.AllRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.BaristaRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.CategoryRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.CoffeeRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.FruitRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.NonCaffeineRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.OwnerTypeRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.SmoothieRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.TeaRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.UserRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.WellBeingRecipeListMediator
import com.zipdabang.zipdabang_android.module.recipes.domain.mediator.ZipdabangRecipeListMediator

class RecipeListRepositoryImpl(
    private val recipeApi: RecipeApi,
    private val database: RecipeDatabase,
    private val dataStore: DataStore<Token>,
    private val isNetworkAvailable: Boolean
): RecipeListRepository {
    override suspend fun getItemCountByCategory(accessToken: String, categoryId: Int, pageIndex: Int): RecipeListDto? {
        return recipeApi.getRecipeListByCategory(accessToken, categoryId, pageIndex = pageIndex, order = "latest")
    }

    override suspend fun getItemCountByOwner(accessToken: String, ownerType: String, pageIndex: Int): RecipeListDto? {
        return recipeApi.getRecipeListByOwnerType(accessToken, ownerType, pageIndex = pageIndex, order = "latest")
    }
    /*    override suspend fun getRecipePreviewList(
            accessToken: String,
            ownerType: String
        ): RecipePreviewItemsDto {
            return recipeApi.getRecipePreview(
                accessToken = accessToken,
                ownerType = ownerType
            )
        }*/

    /*@OptIn(ExperimentalPagingApi::class)
    override fun getRecipeListByOwnerType(
        ownerType: String,
        orderBy: String,
    ): Pager<Int, RecipeItemEntity> {
        val dao = when (ownerType) {
            OwnerType.OFFICIAL.type -> {
                database.recipeZipdabangDao().getAllRecipes()
            }
            OwnerType.INFLUENCER.type -> {
                database.recipeBaristaDao().getAllRecipes()
            }
            OwnerType.USER.type -> {
                database.recipeUserDao().getAllRecipes()
            }
            else -> {
                database.recipeUserDao().getAllRecipes()
            }
        }
        val pagingSourceFactory = {
            dao
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = OwnerTypeRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                ownerType = ownerType,
                orderBy = orderBy
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }*/

    @OptIn(ExperimentalPagingApi::class)
    override fun getZipdabangRecipeList(orderBy: String): Pager<Int, RecipeZipdabangEntity> {
        val pagingSourceFactory = {
            database.recipeZipdabangDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = ZipdabangRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                orderBy = orderBy,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getBaristaRecipeList(orderBy: String): Pager<Int, RecipeBaristaEntity> {
        val pagingSourceFactory = {
            database.recipeBaristaDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = BaristaRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                orderBy = orderBy,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getUserRecipeList(orderBy: String): Pager<Int, RecipeUserEntity> {
        val pagingSourceFactory = {
            database.recipeUserDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UserRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                orderBy = orderBy,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeCoffeeList(orderBy: String): Pager<Int, RecipeCoffeeEntity> {
        val pagingSourceFactory = {
            database.recipeCoffeeDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = CoffeeRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                orderBy = orderBy,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeNonCaffeineList(orderBy: String): Pager<Int, RecipeNonCaffeineEntity> {
        val pagingSourceFactory = {
            database.recipeNonCaffeineDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = NonCaffeineRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                orderBy = orderBy,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeTeaList(orderBy: String): Pager<Int, RecipeTeaEntity> {
        val pagingSourceFactory = {
            database.recipeTeaDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = TeaRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                orderBy = orderBy,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeAdeList(orderBy: String): Pager<Int, RecipeAdeEntity> {
        val pagingSourceFactory = {
            database.recipeAdeDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = AdeRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                orderBy = orderBy,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeSmoothieList(orderBy: String): Pager<Int, RecipeSmoothieEntity> {
        val pagingSourceFactory = {
            database.recipeSmoothieDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = SmoothieRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                orderBy = orderBy,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeFruitList(orderBy: String): Pager<Int, RecipeFruitEntity> {
        val pagingSourceFactory = {
            database.recipeFruitDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = FruitRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                orderBy = orderBy,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeWellBeingList(orderBy: String): Pager<Int, RecipeWellBeingEntity> {
        val pagingSourceFactory = {
            database.recipeWellBeingDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = WellBeingRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                orderBy = orderBy,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeAllList(orderBy: String): Pager<Int, RecipeAllEntity> {
        val pagingSourceFactory = {
            database.recipeAllDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = AllRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                orderBy = orderBy,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    /*@OptIn(ExperimentalPagingApi::class)
    override fun getRecipeListByCategory(
        categoryId: Int,
        orderBy: String
    ): Pager<Int, RecipeItemEntity> {

        Log.d("RecipeList - repository", "${database.recipeListDao().getAllRecipes()}")

        val pagingSourceFactory = {
            database.recipeListDao().getAllRecipes()
        }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = CategoryRecipeListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                categoryId = categoryId,
                orderBy = orderBy
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }*/

    override suspend fun getHotRecipeListByCategory(
        accessToken: String,
        categoryId: Int
    ): ResponseBody<HotRecipeDto> {
        return recipeApi.getHotRecipesByCategory(accessToken, categoryId)
    }

    override suspend fun toggleLikeRemote(
        accessToken: String,
        recipeId: Int
    ): PreferenceResultDto {
        return recipeApi.toggleLike(accessToken, recipeId)
    }

    override suspend fun toggleLikeLocalByUser(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto,
        ownerType: String
    ): PreferenceResultDto {
        return try {
            when (ownerType) {
                OwnerType.OFFICIAL.type -> {
                    val recipeItem = database.recipeZipdabangDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isLiked = !it.isLiked
                        if (it.isLiked) {
                            it.likes += 1
                        } else {
                            it.likes -= 1
                        }
                        database.recipeZipdabangDao().updateRecipe(it)
                    }
                }
                OwnerType.BARISTA.type -> {
                    val recipeItem = database.recipeBaristaDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isLiked = !it.isLiked
                        if (it.isLiked) {
                            it.likes += 1
                        } else {
                            it.likes -= 1
                        }
                        database.recipeBaristaDao().updateRecipe(it)
                    }
                }
                OwnerType.USER.type -> {
                    val recipeItem = database.recipeUserDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isLiked = !it.isLiked
                        if (it.isLiked) {
                            it.likes += 1
                        } else {
                            it.likes -= 1
                        }
                        database.recipeUserDao().updateRecipe(it)
                    }
                }
                else -> {

                }
            }
            preferencesResultDto
        } catch (e: Exception) {
            preferencesResultDto.copy(
                code = e.hashCode(),
                isSuccess = false,
                message = e.message ?: "로컬 처리 중 알 수 없는 에러 발생",
            )
        }
    }

    override suspend fun toggleLikeLocalByCategory(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto,
        categoryId: Int
    ): PreferenceResultDto {
        return try {
            when (categoryId) {
                0 -> {
                    val recipeItem = database.recipeAllDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isLiked = !it.isLiked
                        if (it.isLiked) {
                            it.likes += 1
                        } else {
                            it.likes -= 1
                        }
                        database.recipeAllDao().updateRecipe(it)
                    }
                }
                1 -> {
                    val recipeItem = database.recipeCoffeeDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isLiked = !it.isLiked
                        if (it.isLiked) {
                            it.likes += 1
                        } else {
                            it.likes -= 1
                        }
                        database.recipeCoffeeDao().updateRecipe(it)
                    }
                }
                2 -> {
                    val recipeItem = database.recipeNonCaffeineDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isLiked = !it.isLiked
                        if (it.isLiked) {
                            it.likes += 1
                        } else {
                            it.likes -= 1
                        }
                        database.recipeNonCaffeineDao().updateRecipe(it)
                    }
                }
                3 -> {
                    val recipeItem = database.recipeTeaDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isLiked = !it.isLiked
                        if (it.isLiked) {
                            it.likes += 1
                        } else {
                            it.likes -= 1
                        }
                        database.recipeTeaDao().updateRecipe(it)
                    }
                }
                4 -> {
                    val recipeItem = database.recipeAdeDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isLiked = !it.isLiked
                        if (it.isLiked) {
                            it.likes += 1
                        } else {
                            it.likes -= 1
                        }
                        database.recipeAdeDao().updateRecipe(it)
                    }
                }
                5 -> {
                    val recipeItem = database.recipeSmoothieDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isLiked = !it.isLiked
                        if (it.isLiked) {
                            it.likes += 1
                        } else {
                            it.likes -= 1
                        }
                        database.recipeSmoothieDao().updateRecipe(it)
                    }
                }
                6 -> {
                    val recipeItem = database.recipeFruitDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isLiked = !it.isLiked
                        if (it.isLiked) {
                            it.likes += 1
                        } else {
                            it.likes -= 1
                        }
                        database.recipeFruitDao().updateRecipe(it)
                    }
                }
                7 -> {
                    val recipeItem = database.recipeWellBeingDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isLiked = !it.isLiked
                        if (it.isLiked) {
                            it.likes += 1
                        } else {
                            it.likes -= 1
                        }
                        database.recipeWellBeingDao().updateRecipe(it)
                    }
                }
                else -> {

                }
            }
            preferencesResultDto
        } catch (e: Exception) {
            preferencesResultDto.copy(
                code = e.hashCode(),
                isSuccess = false,
                message = e.message ?: "로컬 처리 중 알 수 없는 에러 발생",
            )
        }
    }

    /*override suspend fun toggleLikeLocal(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto
    ): PreferenceResultDto {
        return try {
            val recipeItem = database.recipeListDao().getRecipeItemById(recipeId)
            recipeItem?.let {
                it.isLiked = !it.isLiked
                if (it.isLiked) {
                    it.likes += 1
                } else {
                    it.likes -= 1
                }
                database.recipeListDao().updateRecipe(it)
            }
            preferencesResultDto
        } catch (e: Exception) {
            preferencesResultDto.copy(
                code = e.hashCode(),
                isSuccess = false,
                message = e.message ?: "로컬 처리 중 알 수 없는 에러 발생",
            )
        }
    }*/

    override suspend fun toggleScrapRemote(
        accessToken: String,
        recipeId: Int
    ): PreferenceResultDto {
        return recipeApi.toggleScrap(accessToken, recipeId)
    }

    override suspend fun toggleScrapLocalByUser(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto,
        ownerType: String
    ): PreferenceResultDto {
        return try {
            when (ownerType) {
                OwnerType.OFFICIAL.type -> {
                    val recipeItem = database.recipeZipdabangDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isScrapped = !it.isScrapped
                        if (it.isScrapped) {
                            it.scraps += 1
                        } else {
                            it.scraps -= 1
                        }
                        database.recipeZipdabangDao().updateRecipe(it)
                    }
                }
                OwnerType.BARISTA.type -> {
                    val recipeItem = database.recipeBaristaDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isScrapped = !it.isScrapped
                        if (it.isScrapped) {
                            it.scraps += 1
                        } else {
                            it.scraps -= 1
                        }
                        database.recipeBaristaDao().updateRecipe(it)
                    }
                }
                OwnerType.USER.type -> {
                    val recipeItem = database.recipeUserDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isScrapped = !it.isScrapped
                        if (it.isScrapped) {
                            it.scraps += 1
                        } else {
                            it.scraps -= 1
                        }
                        database.recipeUserDao().updateRecipe(it)
                    }
                }
                else -> {

                }
            }
            preferencesResultDto
        } catch (e: Exception) {
            preferencesResultDto.copy(
                code = e.hashCode(),
                isSuccess = false,
                message = e.message ?: "로컬 처리 중 알 수 없는 에러 발생",
            )
        }
    }

    override suspend fun toggleScrapLocalByCategory(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto,
        categoryId: Int
    ): PreferenceResultDto {
        return try {
            when (categoryId) {
                0 -> {
                    val recipeItem = database.recipeAllDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isScrapped = !it.isScrapped
                        if (it.isScrapped) {
                            it.scraps += 1
                        } else {
                            it.scraps -= 1
                        }
                        database.recipeAllDao().updateRecipe(it)
                    }
                }
                1 -> {
                    val recipeItem = database.recipeCoffeeDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isScrapped = !it.isScrapped
                        if (it.isScrapped) {
                            it.scraps += 1
                        } else {
                            it.scraps -= 1
                        }
                        database.recipeCoffeeDao().updateRecipe(it)
                    }
                }
                2 -> {
                    val recipeItem = database.recipeNonCaffeineDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isScrapped = !it.isScrapped
                        if (it.isScrapped) {
                            it.scraps += 1
                        } else {
                            it.scraps -= 1
                        }
                        database.recipeNonCaffeineDao().updateRecipe(it)
                    }
                }
                3 -> {
                    val recipeItem = database.recipeTeaDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isScrapped = !it.isScrapped
                        if (it.isScrapped) {
                            it.scraps += 1
                        } else {
                            it.scraps -= 1
                        }
                        database.recipeTeaDao().updateRecipe(it)
                    }
                }
                4 -> {
                    val recipeItem = database.recipeAdeDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isScrapped = !it.isScrapped
                        if (it.isScrapped) {
                            it.scraps += 1
                        } else {
                            it.scraps -= 1
                        }
                        database.recipeAdeDao().updateRecipe(it)
                    }
                }
                5 -> {
                    val recipeItem = database.recipeSmoothieDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isScrapped = !it.isScrapped
                        if (it.isScrapped) {
                            it.scraps += 1
                        } else {
                            it.scraps -= 1
                        }
                        database.recipeSmoothieDao().updateRecipe(it)
                    }
                }
                6 -> {
                    val recipeItem = database.recipeFruitDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isScrapped = !it.isScrapped
                        if (it.isScrapped) {
                            it.scraps += 1
                        } else {
                            it.scraps -= 1
                        }
                        database.recipeFruitDao().updateRecipe(it)
                    }
                }
                7 -> {
                    val recipeItem = database.recipeWellBeingDao().getRecipeItemById(recipeId)
                    recipeItem?.let {
                        it.isScrapped = !it.isScrapped
                        if (it.isScrapped) {
                            it.scraps += 1
                        } else {
                            it.scraps -= 1
                        }
                        database.recipeWellBeingDao().updateRecipe(it)
                    }
                }
                else -> {

                }
            }
            preferencesResultDto
        } catch (e: Exception) {
            preferencesResultDto.copy(
                code = e.hashCode(),
                isSuccess = false,
                message = e.message ?: "로컬 처리 중 알 수 없는 에러 발생",
            )
        }
    }

    /*override suspend fun toggleScrapLocal(
        recipeId: Int,
        preferencesResultDto: PreferenceResultDto
    ): PreferenceResultDto {
        return try {
            val recipeItem = database.recipeListDao().getRecipeItemById(recipeId)
            recipeItem?.let {
                it.isScrapped = !it.isScrapped
                if (it.isScrapped) {
                    it.scraps += 1
                } else {
                    it.scraps -= 1
                }
                database.recipeListDao().updateRecipe(it)
            }
            preferencesResultDto
        } catch (e: Exception) {
            preferencesResultDto.copy(
                code = e.hashCode(),
                isSuccess = false,
                message = e.message ?: "로컬 처리 중 알 수 없는 에러 발생",
            )
        }
    }*/
}