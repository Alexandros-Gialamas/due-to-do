package com.alexandros.p.gialamas.duetodo.ui.screens

import android.content.Context
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
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
import com.alexandros.p.gialamas.duetodo.ui.theme.fabBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.fabContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
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

    val searchBarState: SearchBarState = taskViewModel.searchBarState
    val searchTextState: String = taskViewModel.searchTextState
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = searchTextState) {
        taskViewModel.updateSearchTextState(newSearchTextState = searchTextState)
    }


    val myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor
    val myContentColor = MaterialTheme.colorScheme.myContentColor
    val myTextColor = MaterialTheme.colorScheme.myTextColor
    val myFabBackgroundColor = MaterialTheme.colorScheme.fabBackgroundColor
    val myFabContentColor = MaterialTheme.colorScheme.fabContentColor
    val myFabIconColor = MaterialTheme.colorScheme.fabContentColor // create one

    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        onComplete = { taskViewModel.updateAction(newAction = it) },
        onUndoClicked = { taskViewModel.updateAction(newAction = it) },
        taskTitle = taskViewModel.title,
        action = action,
    )

    MaterialTheme(
        shapes = shapes.copy(HOME_SCREEN_ROUNDED_CORNERS),
        content = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.myBackgroundColor),
                snackbarHost = { SnackbarHost(snackBarHostState) },  // TODO { snackBar don't work }
                containerColor = colorScheme.myBackgroundColor,
                contentColor = colorScheme.myContentColor,
                topBar = {
                    Column(
                        content = {
                            TopBar(
                                onSortClicked = { taskViewModel.persistSortState(it) },
//                                onSearchClicked = { taskViewModel.updateSearchBarState(newSearchBarState = SearchBarState.OPENED) },
                                onMenuItemClicked = {},
                                onDeleteAllTasksClicked = { taskViewModel.updateAction(newAction = Action.DELETE_ALL) },
                                onLayoutClicked = {},
                                onMenuClicked = {},
                                myBackgroundColor = myBackgroundColor,
                                myContentColor = myContentColor,
                                myTextColor = myTextColor
                            )

                            TasksSearchBar(
                                text = searchTextState,
                                onTextChange = { newText ->
                                    taskViewModel.updateSearchTextState(newSearchTextState = newText)
                                },
                                onClearClicked = {
                                    taskViewModel.updateSearchTextState(
                                        newSearchTextState = ""
                                    )
                                },
                                onSearchClicked = { searchQuery ->
                                    taskViewModel.searchDatabase(searchQuery)
                                },
                                textState = searchTextState,
                                myBackgroundColor = myBackgroundColor,
                                myContentColor = myContentColor,
                                myTextColor = myTextColor
                            )
                        }
                    )


                },
                floatingActionButton = {
                    FabButton(
                        onFabClicked = { navigateToTaskScreen(-1) },
                        myFabBackgroundColor = myFabBackgroundColor,
                        myFabContentColor = myFabContentColor,
                        myFabIconColor = myFabIconColor
                    )
                },
                bottomBar = { },
                content = {

                    Surface(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxWidth(),
                        color = Color.Transparent,
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
                                    taskViewModel.updateAction(newAction = action)
                                    taskViewModel.updateDisplayTaskFields(selectedTask = task)
                                    snackBarHostState.currentSnackbarData?.dismiss()
                                },
                                myBackgroundColor = myBackgroundColor,
                                myContentColor = myContentColor,
                                myTextColor =myTextColor
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

