package com.alexandros.p.gialamas.duetodo.ui.components.tasks

import androidx.compose.runtime.Composable
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.screens.EmptyContent

@Composable
fun DisplayTasks(
    taskTableList : List<TaskTable>,
    navigateToTaskScreen : (taskId : Int) -> Unit
){
    if (taskTableList.isEmpty()) {
            EmptyContent()
    } else {
        TaskList(taskTableList = taskTableList, navigateToTaskScreen = navigateToTaskScreen)
    }
}