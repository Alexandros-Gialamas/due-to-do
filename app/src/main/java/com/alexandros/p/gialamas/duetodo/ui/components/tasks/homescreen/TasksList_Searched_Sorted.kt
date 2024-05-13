package com.alexandros.p.gialamas.duetodo.ui.components.tasks.homescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.util.CrudAction
import com.alexandros.p.gialamas.duetodo.util.RequestState
import com.alexandros.p.gialamas.duetodo.util.SearchBarState

@Composable
fun TaskListSearchedAndSorted(
    taskTableList: RequestState<List<TaskTable>>,
    searchedTasks: RequestState<List<TaskTable>>,
    lowTaskPrioritySort: List<TaskTable>,
    highTaskPrioritySort: List<TaskTable>,
    sortState: RequestState<TaskPriority>,
    searchBarState: SearchBarState,
    onSwipeToDelete: (CrudAction, TaskTable) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    isGridLayout : Boolean,
    myBackgroundColor : Color,
    myContentColor : Color,
    myTextColor : Color
) {

    if (sortState is RequestState.Success) {

        when {
            searchBarState == SearchBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    TaskListDisplay(
                        tasks = searchedTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete = onSwipeToDelete,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor,
                        isGridLayout = isGridLayout
                    )
                }
            }

            sortState.data == TaskPriority.NONE -> {
                if (taskTableList is RequestState.Success) {
                    TaskListDisplay(
                        tasks = taskTableList.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete = onSwipeToDelete,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor,
                        isGridLayout = isGridLayout
                    )
                }
            }

            sortState.data == TaskPriority.LOW -> {
                TaskListDisplay(
                    tasks = lowTaskPrioritySort,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete,
                    myBackgroundColor = myBackgroundColor,
                    myContentColor = myContentColor,
                    myTextColor = myTextColor,
                    isGridLayout = isGridLayout
                )
            }

            sortState.data == TaskPriority.HIGH -> {
                TaskListDisplay(
                    tasks = highTaskPrioritySort,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete,
                    myBackgroundColor = myBackgroundColor,
                    myContentColor = myContentColor,
                    myTextColor = myTextColor,
                    isGridLayout = isGridLayout
                )
            }
        }
    }
}