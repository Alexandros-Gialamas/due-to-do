package com.alexandros.p.gialamas.duetodo.ui.components.tasks

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.screens.EmptyContent
import com.alexandros.p.gialamas.duetodo.util.Action

@Composable
fun DisplayedContent(
    tasks: List<TaskTable>,
    onSwipeToDelete: (Action, TaskTable) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    if (tasks.isEmpty()) {
        EmptyContent(
            myBackgroundColor = myBackgroundColor,
            myContentColor = myContentColor,
            myTextColor = myTextColor
        )
    } else {
        TaskItemList(
            taskTableList = tasks,
            navigateToTaskScreen = navigateToTaskScreen,
            onSwipeToDelete = onSwipeToDelete,
            myBackgroundColor = myBackgroundColor,
            myContentColor = myContentColor,
            myTextColor = myTextColor
        )
    }
}