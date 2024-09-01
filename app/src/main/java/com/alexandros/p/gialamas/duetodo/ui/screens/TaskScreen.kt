package com.alexandros.p.gialamas.duetodo.ui.screens

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.unit.LayoutDirection
import com.alexandros.p.gialamas.duetodo.data.models.CheckListItem
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.components.app_bars.bottom_bar.BottomBarTaskScreen
import com.alexandros.p.gialamas.duetodo.ui.components.app_bars.top_bar.TopBarTaskScreen
import com.alexandros.p.gialamas.duetodo.ui.components.tasks.taskscreen.DisplayTask
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction
import com.alexandros.p.gialamas.duetodo.util.SnackToastMessages

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel,
    selectedTask: TaskTable?,
    navigateToHomeScreen: (DatabaseAction) -> Unit,
    context: Context,
) {


    val title = taskViewModel.title
    val category = taskViewModel.category
    val description: String = taskViewModel.description
    val taskPriority = taskViewModel.taskPriority
    val dueDate: Long? = taskViewModel.dueDate
    val isCheckList = taskViewModel.isChecklist
    val isPinned = taskViewModel.isPinned
    val isPopAlarmSelected = taskViewModel.dialogNotification
    val creationDate = taskViewModel.createdDate
    val checkListItems = taskViewModel.checkListItems
    val listItemDescription = taskViewModel.listItemDescription
    val isCompleted = taskViewModel.isCompleted

    val allCategories by taskViewModel.allCategories.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val mySecondBackgroundColor = Brush.myBackgroundBrush(radius = 6800f / 1.1f)
    val myContentColor = MaterialTheme.colorScheme.myContentColor

    BackHandler(true) {
        navigateToHomeScreen(DatabaseAction.NO_ACTION)
    }


    LaunchedEffect(key1 = allCategories) {
        taskViewModel.cleanUnusedCategories()
    }

    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(HOME_SCREEN_ROUNDED_CORNERS),
        content = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                containerColor = Color.Transparent,
                contentColor = myContentColor,
                snackbarHost = { SnackbarHost(snackBarHostState) },
                topBar = {
                    TopBarTaskScreen(
                        selectedTask = selectedTask,
                        navigateToHomeScreen = { databaseAction: DatabaseAction ->
                            if (databaseAction == DatabaseAction.NO_ACTION) {
                                navigateToHomeScreen(databaseAction)
                            } else {
                                if (taskViewModel.validateFields()) {
                                    navigateToHomeScreen(databaseAction)
                                } else {
                                    SnackToastMessages.EMPTY_FIELDS.showToast(context)
                                }
                            }
                        },
                        scope = coroutineScope,
                        keyboardController = keyboardController,
                    )
                },
                bottomBar = {
                    BottomBarTaskScreen(
                        navigateToHomeScreen = { databaseAction: DatabaseAction ->
                            if (databaseAction == DatabaseAction.NO_ACTION) {
                                navigateToHomeScreen(databaseAction)
                            } else {
                                if (taskViewModel.validateFields()) {
                                    navigateToHomeScreen(databaseAction)
                                } else {
                                    SnackToastMessages.EMPTY_FIELDS.showToast(context)
                                }
                            }
                        },
                        title = title,
                        description = description,
                        selectedTask = selectedTask,
                        onCheckListClicked = {
                            when {
                                selectedTask?.checkListItem?.isEmpty() == true
                                        || (checkListItems.isNotEmpty()
                                        && checkListItems.last().listItemDescription.isBlank())-> {
                                    val newList = checkListItems + CheckListItem("", false)
                                    taskViewModel.updateCheckListItems(newList)
                                }
                            }
                            taskViewModel.updateIsCheckList(!isCheckList)
                        },
                        dueDate = dueDate,
                        onDueDateChange = { taskViewModel.updateDueDate(it) },
                        showToastInvalidDate = { SnackToastMessages.INVALID_TIME.showToast(context) },
                        showToastPickADate = { SnackToastMessages.PICK_A_DATE.showToast(context) },
                        onRepeatFrequencyChanged = { /*TODO*/ },
                        context = context,
                        onRemindClicked = { },
                        category = category,
                        taskCategoryList = allCategories,
                        onCategoryClicked = { taskViewModel.updateCategory(it) },
                        taskPriority = taskPriority,
                        onTaskPrioritySelected = { taskViewModel.updateTaskPriority(it) },
                        pin = isPinned,
                        onPinClicked = { taskViewModel.updateIsPinned(!isPinned) },
                        scope = coroutineScope,
                        keyboardController = keyboardController,
                        isCheckList = isCheckList,
                        checkListItems = checkListItems
                    )
                },
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(
                                top = paddingValues.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding(),
                                end = paddingValues.calculateEndPadding(LayoutDirection.Rtl),
                                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr)
                            )
                            .background(mySecondBackgroundColor),
//                            .clickable { LocalFocusManager.current.clearFocus() } ,  // TODO { check focus }
                        content = {
                            DisplayTask(
                                title = title,
                                onTitleChange = { taskViewModel.updateTitle(newTitle = it) },
                                category = category,
                                onCategoryChange = { taskViewModel.updateCategory(newCategory = it) },
                                description = description,
                                onDescriptionChange = {
                                    taskViewModel.updateDescription(
                                        newDescription = it
                                    )
                                },
                                taskPriority = taskPriority,
                                onTaskPriorityChange = {
                                    taskViewModel.updateTaskPriority(
                                        newTaskPriority = it
                                    )
                                },
                                isCheckList = isCheckList,
                                onCheckedChange = { taskViewModel.updateIsCompleted(!isCompleted) },
                                checkListItems = checkListItems,
                                updateCheckListItems = { taskViewModel.updateCheckListItems(it) },
                                viewModel = taskViewModel,
                                listItemDescription = listItemDescription,
                                isCompleted = isCompleted,
                                onListItemDescriptionChange = {
                                    taskViewModel.updateListItemDescription(
                                        it
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