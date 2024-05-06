package com.alexandros.p.gialamas.duetodo.ui.screens

import android.content.Context
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.ui.components.DisplaySnackBar
import com.alexandros.p.gialamas.duetodo.ui.components.TasksSearchBar
import com.alexandros.p.gialamas.duetodo.ui.components.fabbutton.FabButton
import com.alexandros.p.gialamas.duetodo.ui.components.tasks.DisplayTasksList
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.TopBar
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.SCAFFOLD_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrContentColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.Action
import com.alexandros.p.gialamas.duetodo.util.SearchBarState


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    action: Action,
    navController: NavHostController,
    context: Context,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    taskViewModel: TaskViewModel
) {

    LaunchedEffect(key1 = action) {
        taskViewModel.handleDatabaseActions(action = action)
    }

    val allTasks by taskViewModel.allTasks.collectAsState()
    val searchedTasks by taskViewModel.searchedTasks.collectAsState()
    val sortState by taskViewModel.sortState.collectAsState()
    val lowTaskPrioritySort by taskViewModel.lowTaskPrioritySort.collectAsState()
    val highTaskPrioritySort by taskViewModel.highTaskPrioritySort.collectAsState()

    val searchBarState: SearchBarState by taskViewModel.searchBarState
    val searchTextState: String by taskViewModel.searchTextState
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = searchTextState) {
        taskViewModel.searchTextState.value = searchTextState
    }

//    taskViewModel.handleDatabaseActions(action)

//    val actionSnackBar =
    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        onComplete = { taskViewModel.updateAction(newAction = it) },
        onUndoClicked = { taskViewModel.updateAction(newAction = it) },
        taskTitle = taskViewModel.title.value,
        action = action,
        context = context
    )

    MaterialTheme(
        shapes = shapes.copy(HOME_SCREEN_ROUNDED_CORNERS),
        content = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                snackbarHost = { SnackbarHost(snackBarHostState) },  // TODO { snackBar don't work }
                containerColor = colorScheme.topAppBarrBackgroundColor,
                contentColor = colorScheme.topAppBarrContentColor,
                topBar = {
                    Column (
                        content = {
                            TopBar(
                                onSortClicked = { taskViewModel.persistSortState(it) },
                                onSearchClicked = { taskViewModel.searchBarState.value = SearchBarState.OPENED },
                                onMenuItemClicked = {},
                                onDeleteAllTasksClicked = { taskViewModel.updateAction(newAction = Action.DELETE_ALL) },
                                onLayoutClicked = {},
                                onMenuClicked = {}
                            )

                            TasksSearchBar(
                                text = searchTextState,
                                onTextChange = { newText ->
                                    taskViewModel.searchTextState.value = newText
                                },
                                onClearClicked = { taskViewModel.searchTextState.value = "" },
                                onSearchClicked = { searchQuery ->
                                    taskViewModel.searchDatabase(searchQuery)
                                },
                                textState = searchTextState
                            )
                        }
                    )


                },
                floatingActionButton = {
                    FabButton(
                        onFabClicked = { navigateToTaskScreen(-1) },
                        navController = navController
                    )
                },
                bottomBar = {},
                content = {

                    MaterialTheme(
                        content = {
                            Surface(
                                modifier = Modifier
                                    .padding(it)
                                    .fillMaxWidth(),
                                color = Color.Transparent,
//                        modifier = Modifier
//                            .padding(it)
//                            .border(BorderStroke(
//                                color = Color.Gray,
//                                width = 6.dp,
//                                ),
//                                shape = RoundedCornerShape(40.dp)
//                            )
//                        .border(BorderStroke(
//                        color = Color.LightGray,
//                        width = 2.dp,
//                    ),
//                            shape = RoundedCornerShape(40.dp),
//                        ),
//                        color = colorScheme.topAppBarrBackgroundColor,
                                shape = SCAFFOLD_ROUNDED_CORNERS,
                                content = {
                                    DisplayTasksList(
                                        taskTableList = allTasks,
                                        navigateToTaskScreen = navigateToTaskScreen,
                                        searchedTasks = searchedTasks,
                                        searchBarState = searchBarState,
                                        lowTaskPrioritySort = lowTaskPrioritySort,
                                        highTaskPrioritySort = highTaskPrioritySort,
                                        sortState = sortState,
                                        onSwipeToDelete = { action, task ->
//                                            taskViewModel.action.value = action
                                            taskViewModel.updateAction(newAction = action)
                                            taskViewModel.updateDisplayTaskFields(selectedTask = task)
                                            snackBarHostState.currentSnackbarData?.dismiss()
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}


//@Composable
//@Preview
//private fun HomeScreenPreview() {
//
//previewTopBar = true
//
//    HomeScreen(
//        navigateToTask = {},
//        navController = NavHostController(LocalContext.current),
//        context = LocalContext.current,
//        taskViewModel = hiltViewModel()
//    )
//}

