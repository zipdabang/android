package com.zipdabang.zipdabang_android.module.search.data

import com.zipdabang.zipdabang_android.module.search.data.dto.recipecategory.SearchRecipeCategoryDto
import com.zipdabang.zipdabang_android.module.search.data.dto.searchpreview.SearchDto
import com.zipdabang.zipdabang_android.module.search.domain.SearchRepository
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun getSearchPreview(token: String?, keyWord: String?): SearchDto? {
        return searchApi.getSearchPreview(token!!,keyWord!!)
    }

    override suspend fun getSearchRecipeCategoryPreview(
        accessToken: String,
        categoryId: Int,
        keyWord: String,
        pageIndex: Int
    ): SearchRecipeCategoryDto {
        return searchApi.getSearchRecipeCategory(accessToken, categoryId, keyWord, pageIndex)
    }

}