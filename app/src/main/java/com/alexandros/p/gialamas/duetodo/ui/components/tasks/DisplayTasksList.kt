package com.alexandros.p.gialamas.duetodo.ui.components.tasks

import androidx.compose.runtime.Composable
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.Action
import com.alexandros.p.gialamas.duetodo.util.RequestState
import com.alexandros.p.gialamas.duetodo.util.SearchBarState

@Composable
fun DisplayTasksList(
    taskTableList : RequestState<List<TaskTable>>,
    searchedTasks : RequestState<List<TaskTable>>,
    lowTaskPrioritySort : List<TaskTable>,
    highTaskPrioritySort : List<TaskTable>,
    sortState : RequestState<TaskPriority>,
    searchBarState: SearchBarState,
    onSwipeToDelete : (Action, TaskTable) -> Unit,
    navigateToTaskScreen : (taskId : Int) -> Unit,
){

    if (sortState is RequestState.Success){

        when{
            searchBarState == SearchBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success){
                    DisplayedContent(
                        tasks = searchedTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete = onSwipeToDelete
                    )
                }
            }
            sortState.data == TaskPriority.NONE -> {
                if (taskTableList is RequestState.Success) {
                    DisplayedContent(
                        tasks = taskTableList.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete = onSwipeToDelete
                    )
                }
            }
            sortState.data == TaskPriority.LOW -> {
                DisplayedContent(
                    tasks = lowTaskPrioritySort,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
            }
            sortState.data == TaskPriority.HIGH -> {
                DisplayedContent(
                    tasks = highTaskPrioritySort,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
            }

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