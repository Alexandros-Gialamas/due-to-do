package com.alexandros.p.gialamas.duetodo.ui.screens

import android.content.Context
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
import com.alexandros.p.gialamas.duetodo.ui.components.BottomSearchBar
import com.alexandros.p.gialamas.duetodo.ui.components.DisplaySnackBar
import com.alexandros.p.gialamas.duetodo.ui.components.fabbutton.FabButton
import com.alexandros.p.gialamas.duetodo.ui.components.tasks.DisplayTasksList
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.TopBarOrSearchBar
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.SCAFFOLD_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrContentColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.SearchBarState

@Composable
fun HomeScreen(
    navController: NavHostController,
    context: Context,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    taskViewModel: TaskViewModel
) {

    LaunchedEffect(key1 = true) {
        taskViewModel.getAllTasks()
    }

    val action by taskViewModel.action

    val allTasks by taskViewModel.allTasks.collectAsState()
    val searchedTasks by taskViewModel.searchedTasks.collectAsState()
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
        handleDatabaseActions = { taskViewModel.handleDatabaseActions(action) },
        onUndoClicked = { taskViewModel.action.value = it },
        taskTitle = taskViewModel.title.value,
        action = action
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
                            TopBarOrSearchBar(
                                taskViewModel = taskViewModel,
                                searchBarState = searchBarState,
                                searchTextState = searchTextState
                            )

                            BottomSearchBar(
                                text = searchTextState,
                                onTextChange = { newText ->
                                    taskViewModel.searchTextState.value = newText
                                },
                                onClearClicked = { taskViewModel.searchTextState.value = "" },
                                onSearchClicked = { searchQuery ->
                                    taskViewModel.searchDatabase(searchQuery)
                                }
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

