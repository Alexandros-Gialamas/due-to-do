package com.alexandros.p.gialamas.duetodo.ui.components.app_bars.top_bar

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionBack
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionDelete
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionInsert
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionUpdate
import com.alexandros.p.gialamas.duetodo.ui.components.dialogs.DeleteDialog
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.util.CrudAction
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarTaskScreen(
    selectedTask: TaskTable?,
    navigateToHomeScreen: (CrudAction) -> Unit,
    scope: CoroutineScope,
    keyboardController: SoftwareKeyboardController?,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    var openDialog by remember { mutableStateOf(false) }
    val taskExist = selectedTask != null
    val editedBackgroundColor = myBackgroundColor.copy(alpha = 0.7f)
    val mySecondBackgroundColor = Brush.myBackgroundBrush(radius = 6800f / 1.1f)

    if (taskExist){
        DeleteDialog(
            title = stringResource(id = R.string.Delete_Task, selectedTask!!.title), // TODO { asserted call caution }
            message = stringResource(id = R.string.Delete_Task_confirmation, selectedTask.title),
            openDialog = openDialog,
            closeDialog = { openDialog = false },
            onYesClicked = { navigateToHomeScreen(CrudAction.DELETE) },
            myBackgroundColor = myBackgroundColor,
            myContentColor = myContentColor,
            myTextColor = myTextColor
        )
    }



    TopAppBar(
        modifier = Modifier
            .background(mySecondBackgroundColor),
        navigationIcon = {
            ActionBack(
                onBackClicked = navigateToHomeScreen,
                myBackgroundColor = myBackgroundColor,
                myContentColor = myContentColor,
                myTextColor = myTextColor
            )
        },
        title = {
            Text(
                text =
                if (taskExist) {
                    selectedTask!!.title   // TODO { asserted call caution }
                } else {
                    stringResource(id = R.string.New_Task_Top_Bar_Title)
                },
                color = myTextColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(editedBackgroundColor),
        actions = {

            if (taskExist) {

                ActionDelete(
                    onDeleteClicked = { openDialog = true },
                    myBackgroundColor = myBackgroundColor,
                    myContentColor = myContentColor,
                    myTextColor = myTextColor
                )

                ActionUpdate(
                    onUpdateClicked = navigateToHomeScreen,
                    myBackgroundColor = myBackgroundColor,
                    myContentColor = myContentColor,
                    myTextColor = myTextColor
                )

            } else {
                ActionInsert(
                    onAddClicked = navigateToHomeScreen,
                    scope = scope,
                    keyboardController = keyboardController,
                    myBackgroundColor = myBackgroundColor,
                    myContentColor = myContentColor,
                    myTextColor = myTextColor
                )
            }
        }
    )
}