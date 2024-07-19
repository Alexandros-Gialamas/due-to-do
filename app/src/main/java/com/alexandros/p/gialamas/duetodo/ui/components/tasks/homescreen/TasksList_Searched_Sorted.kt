package com.alexandros.p.gialamas.duetodo.ui.components.tasks.homescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.util.DateSortOrder
import com.alexandros.p.gialamas.duetodo.util.RequestState
import com.alexandros.p.gialamas.duetodo.util.SearchBarState

@Composable
fun TaskListSearchedAndSorted(
    modifier: Modifier = Modifier,
    getOverdueTasks: List<TaskTable>,
    taskTableListASC: List<TaskTable>,
    taskTableListDESC: List<TaskTable>,
    searchedTasks: RequestState<List<TaskTable>>,
    categoryLowTaskPriorityASCDateSort : List<TaskTable>,
    categoryLowTaskPriorityDESCDateSort : List<TaskTable>,
    categoryHighTaskPriorityASCDateSort : List<TaskTable>,
    categoryHighTaskPriorityDESCDateSort : List<TaskTable>,
    prioritySortState: RequestState<TaskPriority>,
    dateSortState: RequestState<DateSortOrder>,
    categoryState: RequestState<String>,
    showOverdueTasksState: RequestState<Boolean>,
    searchBarState: SearchBarState,
    searchPhrase : String,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    isGridLayout: RequestState<Boolean>,
) {


    val tasksToDisplay =
        when {
            searchBarState == SearchBarState.TYPING -> {
                if (searchPhrase.isNotBlank()){
                    when (searchedTasks){
                        is RequestState.Success -> {
                            searchedTasks.data.filter { task ->
                                task.title.contains(searchPhrase, ignoreCase = true) ||
                                        task.description.contains(searchPhrase, ignoreCase = true)
                            }
                        } else -> emptyList()
                    }
                }else emptyList()
            }

            ( prioritySortState is RequestState.Success &&
                    dateSortState is RequestState.Success &&
                    categoryState is RequestState.Success &&
                    showOverdueTasksState is RequestState.Success) &&
                    ( searchBarState == SearchBarState.CLEARED || searchBarState == SearchBarState.UNFOCUSED ) -> {
                when {
                    (prioritySortState.data == TaskPriority.NONE) &&
                            (dateSortState.data == DateSortOrder.ASCENDING) -> {
                        if (showOverdueTasksState.data) getOverdueTasks else taskTableListASC
                    }

                    (prioritySortState.data == TaskPriority.NONE) &&
                            (dateSortState.data == DateSortOrder.DESCENDING) -> {
                        if (showOverdueTasksState.data) getOverdueTasks else taskTableListDESC
                    }

                    (prioritySortState.data == TaskPriority.HIGH) &&
                            (dateSortState.data == DateSortOrder.ASCENDING) -> {
                        if (showOverdueTasksState.data) getOverdueTasks else categoryHighTaskPriorityASCDateSort
                    }

                    (prioritySortState.data == TaskPriority.HIGH) &&
                            (dateSortState.data == DateSortOrder.DESCENDING) -> {
                        if (showOverdueTasksState.data) getOverdueTasks else categoryHighTaskPriorityDESCDateSort
                    }

                    (prioritySortState.data == TaskPriority.LOW) &&
                            (dateSortState.data == DateSortOrder.ASCENDING) -> {
                        if (showOverdueTasksState.data) getOverdueTasks else categoryLowTaskPriorityASCDateSort
                    }

                    (prioritySortState.data == TaskPriority.LOW) &&
                            (dateSortState.data == DateSortOrder.DESCENDING) -> {
                        if (showOverdueTasksState.data) getOverdueTasks else categoryLowTaskPriorityDESCDateSort
                    }

                    else -> if (showOverdueTasksState.data) getOverdueTasks else emptyList()
                }
            }

            else ->  emptyList()
        }

    if (isGridLayout is RequestState.Success) {
        TaskListDisplay(
            tasks = tasksToDisplay,
            navigateToTaskScreen = navigateToTaskScreen,
            isGridLayout = isGridLayout.data
        )
    }
}



