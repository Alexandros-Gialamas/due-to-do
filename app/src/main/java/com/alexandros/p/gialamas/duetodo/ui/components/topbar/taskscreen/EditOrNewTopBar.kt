package com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.util.Action

@Composable
fun EditOrNewTopBar(
    selectedTask: TaskTable?,
    navigateToHomeScreen: (Action) -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    if (selectedTask == null) {
        NewTaskTopBar(
            navigateToHomeScreen = navigateToHomeScreen,
            myBackgroundColor = myBackgroundColor,
            myContentColor = myContentColor,
            myTextColor = myTextColor
        )
    } else {
        EditTaskTopBar(
            selectedTask = selectedTask,
            navigateToHomeScreen = navigateToHomeScreen,
            myBackgroundColor = myBackgroundColor,
            myContentColor = myContentColor,
            myTextColor = myTextColor
        )
    }
}