package com.alexandros.p.gialamas.duetodo.ui.components.tasks.homescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexandros.p.gialamas.duetodo.data.models.CheckListItem
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction

@Composable
fun TaskListDisplay(
    modifier: Modifier = Modifier,
    tasks: List<TaskTable>,
    isCheckList: Boolean,
    onCheckListClicked: () -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    isGridLayout : Boolean
) {
    if (tasks.isEmpty()) {
        TaskListEmpty()
    } else {
        TaskListItem(
            taskTableList = tasks,
            navigateToTaskScreen = navigateToTaskScreen,
            isGridLayout = isGridLayout,
            isCheckList = isCheckList,
            onCheckListClicked = { onCheckListClicked() }
        )
    }
}