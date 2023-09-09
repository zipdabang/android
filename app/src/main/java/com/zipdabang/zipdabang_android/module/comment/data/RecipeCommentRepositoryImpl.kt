package com.zipdabang.zipdabang_android.module.comment.data

import androidx.datastore.core.DataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.zipdabang.zipdabang_android.common.Constants
import com.zipdabang.zipdabang_android.core.Paging3Database
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.remotekey.RemoteKeys
import com.zipdabang.zipdabang_android.module.comment.data.local.RecipeCommentEntity
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentContent
import com.zipdabang.zipdabang_android.module.comment.data.remote.PostCommentDto
import com.zipdabang.zipdabang_android.module.comment.domain.CommentMgtResult
import com.zipdabang.zipdabang_android.module.comment.domain.RecipeCommentListMediator
import com.zipdabang.zipdabang_android.module.comment.domain.RecipeCommentRepository
import com.zipdabang.zipdabang_android.module.recipes.data.RecipeApi
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class RecipeCommentRepositoryImpl @Inject constructor(
    private val database: Paging3Database,
    private val recipeApi: RecipeApi,
    private val dataStore: DataStore<Token>,
): RecipeCommentRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getRecipeComments(recipeId: Int): Pager<Int, RecipeCommentEntity> {
        val pagingSourceFactory = {
            database.recipeCommentDao().getAllComments()
        }

        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = RecipeCommentListMediator(
                recipeApi = recipeApi,
                database = database,
                datastore = dataStore,
                recipeId = recipeId
            ),
            pagingSourceFactory = pagingSourceFactory
        )
    }

    override suspend fun postRecipeComment(
        accessToken: String,
        recipeId: Int,
        commentBody: PostCommentContent
    ): PostCommentDto {
        return recipeApi.submitRecipeComment(
            accessToken,
            recipeId,
            commentBody
        )
    }


}