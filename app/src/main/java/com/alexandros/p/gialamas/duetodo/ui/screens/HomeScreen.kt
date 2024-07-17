package com.alexandros.p.gialamas.duetodo.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.ui.components.app_bars.bottom_bar.BottomBarHomeScreen
import com.alexandros.p.gialamas.duetodo.ui.components.fabbutton.FabButton
import com.alexandros.p.gialamas.duetodo.ui.components.app_bars.top_bar.SearchBarHomeScreen
import com.alexandros.p.gialamas.duetodo.ui.components.drawer.Drawer
import com.alexandros.p.gialamas.duetodo.ui.components.snackbar.DisplaySnackBar
import com.alexandros.p.gialamas.duetodo.ui.components.tasks.homescreen.TaskListSearchedAndSorted
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.SCAFFOLD_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction
import com.alexandros.p.gialamas.duetodo.util.DateSortOrder
import com.alexandros.p.gialamas.duetodo.util.RequestState
import com.alexandros.p.gialamas.duetodo.util.SearchBarState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen(
    databaseAction: DatabaseAction,
    navController: NavHostController,
    context: Context,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    taskViewModel: TaskViewModel
) {
    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
//        if (!isGranted){
//            shouldShowRequestPermissionRationale()
//        }
        }
    )




    LaunchedEffect(key1 = databaseAction) {
        taskViewModel.handleDatabaseActions(databaseAction = databaseAction)
    }


    val allCategories by taskViewModel.allCategories.collectAsState()
    val searchedTasks by taskViewModel.allTasks.collectAsState()
    val prioritySortState by taskViewModel.prioritySortState.collectAsState()
    val dateSortState by taskViewModel.dateSortState.collectAsState()
    val categoryState by taskViewModel.categoryState.collectAsState()
    val allTasksASC by taskViewModel.sortByCategoryDateASC(categoryState).collectAsState(emptyList())
    val allTasksDESC by taskViewModel.sortByCategoryDateDESC(categoryState).collectAsState(emptyList())
    val categoryLowTaskPriorityASCDateSort by taskViewModel.sortByCategoryLowPriorityDateASC(categoryState).collectAsState(emptyList())
    val categoryLowTaskPriorityDESCDateSort by taskViewModel.sortByCategoryLowPriorityDateDESC(categoryState).collectAsState(emptyList())
    val categoryHighTaskPriorityASCDateSort by taskViewModel.sortByCategoryHighPriorityDateASC(categoryState).collectAsState(emptyList())
    val categoryHighTaskPriorityDESCDateSort by taskViewModel.sortByCategoryHighPriorityDateDESC(categoryState).collectAsState(emptyList())
    val isGridLayout by taskViewModel.isGridLayout.collectAsState()

    val searchBarState: SearchBarState = taskViewModel.searchBarState
    val searchTextState: String = taskViewModel.searchTextState

    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current


    val snackBarHostState = remember { SnackbarHostState() }
    var drawerState = rememberDrawerState(DrawerValue.Closed)

    val dueDate: Long? = taskViewModel.dueDate
    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val formattedDate = dueDate?.let { dateFormat.format(it) } ?: "No Date"
    val formattedTime = dueDate?.let { timeFormat.format(it) } ?: "No Time"

    val mySecondBackgroundColor = Brush.myBackgroundBrush(radius = 6800f / 1.1f)
    val myContentColor = MaterialTheme.colorScheme.myContentColor


    LaunchedEffect(key1 = searchTextState) {
        taskViewModel.updateSearchTextState(newSearchTextState = searchTextState)
    }

    LaunchedEffect(key1 = allCategories) {
        taskViewModel.cleanUnusedCategories()
    }



    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        scope = coroutineScope,
        onComplete = { taskViewModel.updateAction(newDatabaseAction = it) },
        onUndoClicked = { taskViewModel.updateAction(newDatabaseAction = it) },
        taskTitle = taskViewModel.title,
        databaseAction = databaseAction,
    )

    MaterialTheme(
        shapes = shapes.copy(HOME_SCREEN_ROUNDED_CORNERS),
        content = {
            Drawer(
                drawerState = drawerState,
                scope = coroutineScope,
                onThemeSelected = {},
                onSignInToGoogle = { /*TODO*/ },
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
                                        searchPhrase = searchTextState,
                                        onSearchPhraseChanged = { newText, searchBarState ->
                                            taskViewModel.updateSearchTextState(newSearchTextState = newText)
                                            taskViewModel.updateSearchBarState(searchBarState)
                                        },
                                        onClearClicked = {
                                            taskViewModel.updateSearchTextState(
                                                newSearchTextState = ""

                                            )
                                            taskViewModel.updateSearchBarState(it)
                                        },
                                        onSearchClicked = { searchQuery ->
//                                            taskViewModel.searchDatabase(searchQuery)  // TODO { check this }
                                        },
                                        onDeleteAllTasksClicked = {
                                            taskViewModel.updateAction(
                                                newDatabaseAction = DatabaseAction.DELETE_ALL
                                            )
                                        },
                                        onMenuClicked = {
                                            coroutineScope.launch {
                                                if (drawerState.isOpen) drawerState.close() else drawerState.open()
                                            }
                                        },
                                    )
                                }
                            )
                        },
                        floatingActionButton = {
                            FabButton(
                                onFabClicked = { navigateToTaskScreen(-1) },
                            )
                        },
                        bottomBar = {
                            BottomBarHomeScreen(
                                taskCategories = allCategories,
                                onCategoryClicked = { selectedCategory ->
                                    taskViewModel.persistCategoryState(selectedCategory) },
                                onPrioritySortClicked = { priorityOrder ->
                                    taskViewModel.persistPrioritySortState(priorityOrder)
                                },
                                onDateSortClicked = { dateOrder ->
                                    taskViewModel.persistDateSortState(dateOrder)
                                },
                                onLayoutClicked = { taskViewModel.enableGridLayout() },
                                onNewCheckListClicked = {

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                    }
                                },
                                prioritySortState = prioritySortState,
                                dateSortOrder = dateSortState,
                                categoryState = categoryState,
                                isGridLayout = isGridLayout,
                            )
                        },
                        content = { paddingValues ->

                            Surface(
                                modifier = Modifier
                                    .padding(
                                        top = paddingValues.calculateTopPadding(),
                                        bottom = paddingValues.calculateBottomPadding(),
                                        end = paddingValues.calculateEndPadding(LayoutDirection.Rtl),
                                        start = paddingValues.calculateStartPadding(LayoutDirection.Ltr)
                                    )
                                    .fillMaxWidth(),
                                color = Color.Transparent,
                                shape = SCAFFOLD_ROUNDED_CORNERS,
                                content = {
                                    TaskListSearchedAndSorted(
                                        modifier = Modifier
                                            .padding(
                                                top = paddingValues.calculateTopPadding(),
                                                bottom = paddingValues.calculateBottomPadding(),
                                                end = paddingValues.calculateEndPadding(
                                                    LayoutDirection.Rtl
                                                ),
                                                start = paddingValues.calculateStartPadding(
                                                    LayoutDirection.Ltr
                                                )
                                            ),
                                        taskTableListASC = allTasksASC,
                                        taskTableListDESC = allTasksDESC,
                                        navigateToTaskScreen = navigateToTaskScreen,
                                        searchedTasks = searchedTasks,
                                        searchBarState = searchBarState,
                                        searchPhrase = searchTextState,
                                        categoryLowTaskPriorityASCDateSort = categoryLowTaskPriorityASCDateSort,
                                        categoryLowTaskPriorityDESCDateSort = categoryLowTaskPriorityDESCDateSort,
                                        categoryHighTaskPriorityASCDateSort = categoryHighTaskPriorityASCDateSort,
                                        categoryHighTaskPriorityDESCDateSort = categoryHighTaskPriorityDESCDateSort,
                                        prioritySortState = prioritySortState,
                                        dateSortState = dateSortState,
                                        categoryState = categoryState,
                                        isGridLayout = isGridLayout
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

