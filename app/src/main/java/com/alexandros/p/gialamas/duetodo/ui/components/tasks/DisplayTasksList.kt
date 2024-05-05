package com.alexandros.p.gialamas.duetodo.ui.components.tasks

import androidx.compose.runtime.Composable
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.util.RequestState
import com.alexandros.p.gialamas.duetodo.util.SearchBarState

@Composable
fun DisplayTasksList(
    taskTableList : RequestState<List<TaskTable>>,
    searchedTasks : RequestState<List<TaskTable>>,
    searchBarState: SearchBarState,
    navigateToTaskScreen : (taskId : Int) -> Unit,
){
    if (searchBarState == SearchBarState.TRIGGERED){
        if (searchedTasks is RequestState.Success){
            DisplayedContent(
                tasks = searchedTasks.data,
                navigateToTaskScreen = navigateToTaskScreen,
            )
        }
    } else {
        if (taskTableList is RequestState.Success) {
            DisplayedContent(
                tasks = taskTableList.data,
                navigateToTaskScreen = navigateToTaskScreen,
            )
        }
    }
//    if (taskTableList is RequestState.Success){
//        if (taskTableList.data.isEmpty()) {
//            EmptyContent()
//        } else {
//            TaskItemList(taskTableList = taskTableList.data, navigateToTaskScreen = navigateToTaskScreen)
//        }
//    }
}