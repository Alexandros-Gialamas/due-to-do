package com.alexandros.p.gialamas.duetodo.ui.components.tasks.homescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.util.CrudAction

@Composable
fun TaskListDisplay(
    tasks: List<TaskTable>,
    onSwipeToDelete: (CrudAction, TaskTable) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    isGridLayout : Boolean,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    if (tasks.isEmpty()) {
        TaskListEmpty(
            myBackgroundColor = myBackgroundColor,
            myContentColor = myContentColor,
            myTextColor = myTextColor
        )
    } else {
        TaskListItem(
            taskTableList = tasks,
            navigateToTaskScreen = navigateToTaskScreen,
            onSwipeToDelete = onSwipeToDelete,
            myBackgroundColor = myBackgroundColor,
            myContentColor = myContentColor,
            myTextColor = myTextColor,
            isGridLayout = isGridLayout
        )
    }
}