package com.alexandros.p.gialamas.duetodo.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.ui.components.app_bars.bottom_bar.BottomBarHomeScreen
import com.alexandros.p.gialamas.duetodo.ui.components.fabbutton.FabButton
import com.alexandros.p.gialamas.duetodo.ui.components.app_bars.top_bar.SearchBarHomeScreen
import com.alexandros.p.gialamas.duetodo.ui.components.snackbar.DisplaySnackBar
import com.alexandros.p.gialamas.duetodo.ui.components.tasks.homescreen.TaskListSearchedAndSorted
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.SCAFFOLD_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.fabBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.fabContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.CrudAction
import com.alexandros.p.gialamas.duetodo.util.SearchBarState
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun HomeScreen(
    crudAction: CrudAction,
    navController: NavHostController,
    context: Context,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    taskViewModel: TaskViewModel
) {

    LaunchedEffect(key1 = crudAction) {
        taskViewModel.handleDatabaseActions(crudAction = crudAction)
    }


    val allCategories by taskViewModel.allCategories.collectAsState()
    val allTasks by taskViewModel.allTasks.collectAsState()
    val searchedTasks by taskViewModel.searchedTasks.collectAsState()
    val sortState by taskViewModel.sortState.collectAsState()
    val lowTaskPrioritySort by taskViewModel.lowTaskPrioritySort().collectAsState()
    val highTaskPrioritySort by taskViewModel.highTaskPrioritySort().collectAsState()
    val isGridLayout by taskViewModel.isGridLayout.collectAsState()

    val searchBarState: SearchBarState = taskViewModel.searchBarState
    val searchTextState: String = taskViewModel.searchTextState

    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current


    val snackBarHostState = remember { SnackbarHostState() }

    val dueDate: Long? = taskViewModel.dueDate
    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val formattedDate = dueDate?.let { dateFormat.format(it) } ?: "No Date"
    val formattedTime = dueDate?.let { timeFormat.format(it) } ?: "No Time"

    val mySecondBackgroundColor = Brush.myBackgroundBrush(radius = 6800f / 1.1f)
    val myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor
    val myContentColor = MaterialTheme.colorScheme.myContentColor
    val myTextColor = MaterialTheme.colorScheme.myTextColor
    val myFabBackgroundColor = MaterialTheme.colorScheme.fabBackgroundColor
    val myFabContentColor = MaterialTheme.colorScheme.fabContentColor
    val myFabIconColor = MaterialTheme.colorScheme.fabContentColor // TODO { create one }

    LaunchedEffect(key1 = searchTextState) {
        taskViewModel.updateSearchTextState(newSearchTextState = searchTextState)
    }

    LaunchedEffect(key1 = allCategories) {
        taskViewModel.cleanUnusedCategories()
    }



    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        scope = coroutineScope,
        onComplete = { taskViewModel.updateAction(newCrudAction = it) },
        onUndoClicked = { taskViewModel.updateAction(newCrudAction = it) },
        taskTitle = taskViewModel.title,
        crudAction = crudAction,
    )

    MaterialTheme(
        shapes = shapes.copy(HOME_SCREEN_ROUNDED_CORNERS),
        content = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(mySecondBackgroundColor),
                containerColor = Color.Transparent,
                contentColor = myContentColor,
                snackbarHost = { SnackbarHost(snackBarHostState) },
                topBar = {
                    Column(
                        content = {

                            SearchBarHomeScreen(
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
                                onDeleteAllTasksClicked = { taskViewModel.updateAction(newCrudAction = CrudAction.DELETE_ALL) },
                                onMenuClicked = { /*TODO */ },
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
                bottomBar = {
                    BottomBarHomeScreen(
                        taskCategories = allCategories,
                        onCategoryClicked = {},
                        onSortClicked = { taskViewModel.persistSortState(it) },
                        onLayoutClicked = { taskViewModel.enableGridLayout() },
                        onNewCheckListClicked = { /* TODO */ },
                        isGridLayout = isGridLayout,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )
                },
                content = {

                    Surface(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxWidth(),
                        color = Color.Transparent,
                        shape = SCAFFOLD_ROUNDED_CORNERS,
                        content = {
                            TaskListSearchedAndSorted(
                                taskTableList = allTasks,
                                navigateToTaskScreen = navigateToTaskScreen,
                                searchedTasks = searchedTasks,
                                searchBarState = searchBarState,
                                lowTaskPrioritySort = lowTaskPrioritySort,
                                highTaskPrioritySort = highTaskPrioritySort,
                                sortState = sortState,
                                onSwipeToDelete = { action, task ->
                                    taskViewModel.updateAction(newCrudAction = action)
                                    taskViewModel.updateDisplayTaskFields(selectedTask = task)
                                    snackBarHostState.currentSnackbarData?.dismiss()
                                },
                                myBackgroundColor = myBackgroundColor,
                                myContentColor = myContentColor,
                                myTextColor = myTextColor,
                                isGridLayout = isGridLayout
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

