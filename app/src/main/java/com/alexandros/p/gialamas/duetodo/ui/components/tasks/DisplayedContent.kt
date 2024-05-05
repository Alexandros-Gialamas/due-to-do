package com.alexandros.p.gialamas.duetodo.ui.components.tasks

import androidx.compose.runtime.Composable
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.screens.EmptyContent
import com.alexandros.p.gialamas.duetodo.util.RequestState

@Composable
 fun DisplayedContent(
    tasks : List<TaskTable>,
    navigateToTaskScreen : (taskId : Int) -> Unit,
){
        if (tasks.isEmpty()) {
            EmptyContent()
        } else {
            TaskItemList(
                taskTableList = tasks,
                navigateToTaskScreen = navigateToTaskScreen,
            )
        }
}