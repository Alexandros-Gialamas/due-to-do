package com.alexandros.p.gialamas.duetodo.ui.components.tasks.homescreen

import android.app.DownloadManager.Request
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction
import com.alexandros.p.gialamas.duetodo.util.DateSortOrder
import com.alexandros.p.gialamas.duetodo.util.RequestState
import com.alexandros.p.gialamas.duetodo.util.SearchBarState
import com.alexandros.p.gialamas.duetodo.util.SelectedCategoryState
import kotlinx.coroutines.flow.combine

@Composable
fun TaskListSearchedAndSorted(
    modifier: Modifier = Modifier,
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
    searchBarState: SearchBarState,
    searchPhrase : String,
    onSwipeToDelete: (DatabaseAction, TaskTable) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    isGridLayout: Boolean
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
                    categoryState is RequestState.Success ) &&
                    ( searchBarState == SearchBarState.CLEARED || searchBarState == SearchBarState.UNFOCUSED ) -> {
                when {
                    (prioritySortState.data == TaskPriority.NONE) &&
                            (dateSortState.data == DateSortOrder.ASCENDING) -> {
                        taskTableListASC
                    }

                    (prioritySortState.data == TaskPriority.NONE) &&
                            (dateSortState.data == DateSortOrder.DESCENDING) -> {
                        taskTableListDESC
                    }

                    (prioritySortState.data == TaskPriority.HIGH) &&
                            (dateSortState.data == DateSortOrder.ASCENDING) -> {
                        categoryHighTaskPriorityASCDateSort
                    }

                    (prioritySortState.data == TaskPriority.HIGH) &&
                            (dateSortState.data == DateSortOrder.DESCENDING) -> {
                        categoryHighTaskPriorityDESCDateSort
                    }

                    (prioritySortState.data == TaskPriority.LOW) &&
                            (dateSortState.data == DateSortOrder.ASCENDING) -> {
                        categoryLowTaskPriorityASCDateSort
                    }

                    (prioritySortState.data == TaskPriority.LOW) &&
                            (dateSortState.data == DateSortOrder.DESCENDING) -> {
                        categoryLowTaskPriorityDESCDateSort
                    }

                    else -> emptyList()
                }
            }

            else -> emptyList()
        }

    TaskListDisplay(
        tasks = tasksToDisplay,
        onSwipeToDelete = onSwipeToDelete,
        navigateToTaskScreen = navigateToTaskScreen,
        isGridLayout = isGridLayout
    )

}



