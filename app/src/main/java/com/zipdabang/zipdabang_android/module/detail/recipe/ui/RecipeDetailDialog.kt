package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.recipes.common.ReportContent
import com.zipdabang.zipdabang_android.ui.component.CommentReportDialog
import com.zipdabang.zipdabang_android.ui.component.RecipeReportDialog
import com.zipdabang.zipdabang_android.ui.component.UserBlockDialog

@Composable
fun RecipeDetailDialog(
    recipeReportActivated: Boolean,
    recipeId: Int,
    recipeReportId: Int,
    setRecipeReport: (Boolean) -> Unit,
    onClickRecipeReport: (Int) -> Unit,
    onRecipeReportContentChange: (Int) -> Unit,
    recipeBlockActivated: Boolean,
    setRecipeBlock: (Boolean) -> Unit,
    onClickRecipeBlock: () -> Unit,
    commentReportActivated: Boolean,
    commentIdForReport: Int,
    commentReportId: Int,
    setCommentReport: (Boolean) -> Unit,
    onClickCommentReport: (Int, Int) -> Unit,
    onCommentReportContentChange: (Int) -> Unit,
    commentBlockActivated: Boolean,
    setCommentBlock: (Boolean) -> Unit,
    onClickCommentBlock: () -> Unit
) {
    if (recipeReportActivated) {
        RecipeReportDialog(
            title = stringResource(R.string.alert_report_recipe),
            recipeId = recipeId,
            reportId = recipeReportId,
            declineText = stringResource(R.string.alert_cancel),
            acceptText = stringResource(R.string.alert_report),
            reportContentList = ReportContent.contents,
            setShowDialog = { changedState ->
                setRecipeReport(changedState)
            },
            onReportClick = onClickRecipeReport,
            onReportContentChange = onRecipeReportContentChange
        )
    }

    if (recipeBlockActivated) {
        UserBlockDialog(
            title = stringResource(id = R.string.alert_block_user),
            text = stringResource(R.string.alert_block_content),
            declineText = stringResource(R.string.alert_cancel),
            acceptText = stringResource(id = R.string.alert_block_user),
            setShowDialog = { changedState ->
                setRecipeBlock(changedState)
            },
            onAcceptClick = onClickRecipeBlock
        )
    }

    if (commentReportActivated) {
        CommentReportDialog(
            title = stringResource(R.string.alert_report_comment),
            recipeId = recipeId,
            commentId = commentIdForReport,
            reportId = commentReportId,
            declineText = stringResource(R.string.alert_cancel),
            acceptText = stringResource(R.string.alert_report),
            reportContentList = ReportContent.contents,
            setShowDialog = setCommentReport,
            onReportClick = onClickCommentReport,
            onReportContentChange = onCommentReportContentChange
        )
    }

    if (commentBlockActivated) {
        UserBlockDialog(
            title = stringResource(id = R.string.alert_block_user),
            text = stringResource(R.string.alert_block_content),
            declineText = stringResource(R.string.alert_cancel),
            acceptText = stringResource(id = R.string.alert_block_user),
            setShowDialog = setCommentBlock,
            onAcceptClick = onClickCommentBlock
        )
    }
}

