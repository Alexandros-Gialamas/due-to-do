package com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions.CloseAction
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions.EditTaskTopBarActions
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.util.Action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskTopBar(
    selectedTask: TaskTable?,
    navigateToHomeScreen: (Action) -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    val editedBackgroundColor = myBackgroundColor.copy(alpha = 0.7f)
    val mySecondBackgroundColor = Brush.myBackgroundBrush(radius = 6800f / 1.1f)


    TopAppBar(
        modifier = Modifier
            .background(mySecondBackgroundColor),
        navigationIcon = {
            CloseAction(
                onCloseClicked = navigateToHomeScreen,
                myBackgroundColor = myBackgroundColor,
                myContentColor = myContentColor,
                myTextColor = myTextColor
            )
        },
        title = {
            selectedTask?.title?.let {
                Text(
                    text = it,
                    color = myTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(editedBackgroundColor),
        actions = {
            EditTaskTopBarActions(
                selectedTask = selectedTask,
                navigateToHomeScreen = navigateToHomeScreen,
                myBackgroundColor = myBackgroundColor,
                myContentColor = myContentColor,
                myTextColor = myTextColor
            )
        }
    )
}


@Composable
@Preview
private fun EditTaskTopBarPreview() {
    EditTaskTopBar(
        navigateToHomeScreen = {},
        selectedTask = TaskTable(
            taskId = 0,
            title = "Go to Gym",
            description = "Tomorrow at 5 pm",
            taskPriority = TaskPriority.MEDIUM,
        ),
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        myTextColor = MaterialTheme.colorScheme.myTextColor
    )
}