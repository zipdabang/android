package com.zipdabang.zipdabang_android.common

import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.zipdabang.zipdabang_android.module.comment.ui.PostCommentState
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentPage
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentState
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailDomain
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeInfoPage
import com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search.FollowInfoDB
import com.zipdabang.zipdabang_android.module.my.ui.FollowScreen
import com.zipdabang.zipdabang_android.module.my.ui.FollowingScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyPagerInfoScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyPagerProfileScreen
import com.zipdabang.zipdabang_android.module.my.ui.MyPagerRecipesScreen
import com.zipdabang.zipdabang_android.module.my.ui.RecipeForOthers
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.hot.HotRecipeList
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState

typealias ComposableFun = @Composable () -> Unit
sealed class TabItem(val tabTitle: String, val screen: ComposableFun) {

    class RecipeInfo(
        private val recipeDetailData: RecipeDetailDomain?,
        // private val onClickCart: (String) -> Unit
    ): TabItem(
        tabTitle = "상세 정보",
        screen = { RecipeInfoPage(recipeDetailData) }
    )
    class Comment(
        val comments: Int,
        val recipeId: Int,
        private val postResult: PostCommentState,
        private val commentItems: LazyPagingItems<RecipeCommentState>,
        private val onClickEdit: (Int, String) -> Unit,
        private val onClickDelete: (Int) -> Unit,
        private val showCommentReport: (Int, Int, Int) -> Unit,
        private val showCommentBlock: (Int) -> Unit
    ) : TabItem(
        tabTitle = "댓글 ${comments}개",
        screen = {
            RecipeCommentPage(
                commentCount = comments,
                recipeId = recipeId,
                onClickDelete = onClickDelete,
                onClickEdit = onClickEdit,
                showCommentReport = showCommentReport,
                showCommentBlock = showCommentBlock,
                postResult = postResult,
                comments = commentItems
            )
        }
    )

    class MyProfile() : TabItem(
        tabTitle = "프로필",
        screen = {
            MyPagerProfileScreen()
        }
    )

    class MyRecipes(
        onClickMyrecipe : ()->Unit,
    ) : TabItem(
        tabTitle = "게시글",
        screen = {
            MyPagerRecipesScreen(
                onClickMyrecipe=onClickMyrecipe,
            )
        }
    )

    class MyInfo(
        onClickLike: ()->Unit,
        onClickScrap: () -> Unit,
        onClickMyrecipe: () -> Unit,
        onClickShopping: () -> Unit,
        onClickNotice: () -> Unit,
        onClickAlarm : () -> Unit,
        onClickInquiry : () -> Unit,
        onClickLogout: () -> Unit,
        onClickUserInfo : ()->Unit,
    ) : TabItem(
        tabTitle = "개인정보",
        screen = {
            MyPagerInfoScreen(
                onClickLike= onClickLike,
                onClickScrap= onClickScrap,
                onClickMyrecipe= onClickMyrecipe,
                onClickShopping= onClickShopping,
                onClickNotice= onClickNotice,
                onClickAlarm = onClickAlarm,
                onClickInquiry = onClickInquiry,
                onClickLogout= onClickLogout,
                onClickUserInfo = onClickUserInfo
            )
        }
    )

    class followList(
        onClickOthers: (Int) -> Unit,
        searchFollowItem: LazyPagingItems<FollowInfoDB>?
    ) : TabItem(
        tabTitle =  "팔로우",
        screen = {
            FollowScreen(
              onClickOthers= onClickOthers,
                searchFollowItem = searchFollowItem
            )
        }
    )

    class followingList(
        onClickOthers : (Int) -> Unit
    ) : TabItem(
        tabTitle = "팔로잉",
        screen = {
            FollowingScreen(
                onClickOthers = onClickOthers
            )
        }
    )

    class Coffee(
        hotItems:  UiState<List<HotRecipeItem>>,
        onRecipeClick: (Int) -> Unit,
        onScrapClick: (Int) -> Unit,
        onLikeClick: (Int) -> Unit,
        likeState: PreferenceToggleState,
        scrapState: PreferenceToggleState
    ): TabItem(
        tabTitle = "커피",
        screen = {
            HotRecipeList(
                hotItems = hotItems,
                onRecipeClick = onRecipeClick,
                onScrapClick = onScrapClick,
                onLikeClick = onLikeClick,
                likeState = likeState,
                scrapState = scrapState
            )
        }
    )

    class CaffeineFree(
        hotItems:  UiState<List<HotRecipeItem>>,
        onRecipeClick: (Int) -> Unit,
        onScrapClick: (Int) -> Unit,
        onLikeClick: (Int) -> Unit,
        likeState: PreferenceToggleState,
        scrapState: PreferenceToggleState
    ): TabItem(
        tabTitle = "논카페인",
        screen = {
            HotRecipeList(
                hotItems = hotItems,
                onRecipeClick = onRecipeClick,
                onScrapClick = onScrapClick,
                onLikeClick = onLikeClick,
                likeState = likeState,
                scrapState = scrapState
            )
        }
    )

    class Tea(
        hotItems:  UiState<List<HotRecipeItem>>,
        onRecipeClick: (Int) -> Unit,
        onScrapClick: (Int) -> Unit,
        onLikeClick: (Int) -> Unit,
        likeState: PreferenceToggleState,
        scrapState: PreferenceToggleState
    ): TabItem(
        tabTitle = "차",
        screen = {
            HotRecipeList(
                hotItems = hotItems,
                onRecipeClick = onRecipeClick,
                onScrapClick = onScrapClick,
                onLikeClick = onLikeClick,
                likeState = likeState,
                scrapState = scrapState
            )
        }
    )

    class Ade(
        hotItems:  UiState<List<HotRecipeItem>>,
        onRecipeClick: (Int) -> Unit,
        onScrapClick: (Int) -> Unit,
        onLikeClick: (Int) -> Unit,
        likeState: PreferenceToggleState,
        scrapState: PreferenceToggleState
    ): TabItem(
        tabTitle = "에이드",
        screen = {
            HotRecipeList(
                hotItems = hotItems,
                onRecipeClick = onRecipeClick,
                onScrapClick = onScrapClick,
                onLikeClick = onLikeClick,
                likeState = likeState,
                scrapState = scrapState
            )
        }
    )

    class Smoothie(
        hotItems:  UiState<List<HotRecipeItem>>,
        onRecipeClick: (Int) -> Unit,
        onScrapClick: (Int) -> Unit,
        onLikeClick: (Int) -> Unit,
        likeState: PreferenceToggleState,
        scrapState: PreferenceToggleState
    ): TabItem(
        tabTitle = "스무디",
        screen = {
            HotRecipeList(
                hotItems = hotItems,
                onRecipeClick = onRecipeClick,
                onScrapClick = onScrapClick,
                onLikeClick = onLikeClick,
                likeState = likeState,
                scrapState = scrapState
            )
        }
    )

    class Fruit(
        hotItems:  UiState<List<HotRecipeItem>>,
        onRecipeClick: (Int) -> Unit,
        onScrapClick: (Int) -> Unit,
        onLikeClick: (Int) -> Unit,
        likeState: PreferenceToggleState,
        scrapState: PreferenceToggleState
    ): TabItem(
        tabTitle = "과일음료",
        screen = {
            HotRecipeList(
                hotItems = hotItems,
                onRecipeClick = onRecipeClick,
                onScrapClick = onScrapClick,
                onLikeClick = onLikeClick,
                likeState = likeState,
                scrapState = scrapState
            )
        }
    )

    class WellBeing(
        hotItems:  UiState<List<HotRecipeItem>>,
        onRecipeClick: (Int) -> Unit,
        onScrapClick: (Int) -> Unit,
        onLikeClick: (Int) -> Unit,
        likeState: PreferenceToggleState,
        scrapState: PreferenceToggleState
    ): TabItem(
        tabTitle = "건강음료",
        screen = {
            HotRecipeList(
                hotItems = hotItems,
                onRecipeClick = onRecipeClick,
                onScrapClick = onScrapClick,
                onLikeClick = onLikeClick,
                likeState = likeState,
                scrapState = scrapState
            )
        }
    )

    class All(
        hotItems:  UiState<List<HotRecipeItem>>,
        onRecipeClick: (Int) -> Unit,
        onScrapClick: (Int) -> Unit,
        onLikeClick: (Int) -> Unit,
        likeState: PreferenceToggleState,
        scrapState: PreferenceToggleState
    ): TabItem(
        tabTitle = "전체",
        screen = {
            HotRecipeList(
                hotItems = hotItems,
                onRecipeClick = onRecipeClick,
                onScrapClick = onScrapClick,
                onLikeClick = onLikeClick,
                likeState = likeState,
                scrapState = scrapState
            )
        }
    )


    //다른 사람 집다방

    class ProfileForOthers() : TabItem(
        tabTitle =  "프로필",
        screen = {
            com.zipdabang.zipdabang_android.module.my.ui.ProfileForOthers()//다른사람 프로필
        }
    )

    class RecipesForOthers(
        nickname : String
    ) : TabItem(
        tabTitle =  "레시피",
        screen = {
            RecipeForOthers(nickname)
        }
    )





}

sealed class HotRecipeTabItem(val tabTitle: String, val screen: ComposableFun) {

}