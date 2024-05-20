package com.alexandros.p.gialamas.duetodo.ui.screens

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.zIndex
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.components.app_bars.bottom_bar.BottomBarTaskScreen
import com.alexandros.p.gialamas.duetodo.ui.components.app_bars.top_bar.TopBarTaskScreen
import com.alexandros.p.gialamas.duetodo.ui.components.tasks.taskscreen.DisplayTask
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.CrudAction
import com.alexandros.p.gialamas.duetodo.util.SettingAction
import com.alexandros.p.gialamas.duetodo.util.SnackToastMessages
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel,
    selectedTask: TaskTable?,
    navigateToHomeScreen: (CrudAction) -> Unit,
    context: Context,
) {


    val title = taskViewModel.title
    val category = taskViewModel.category
    val description: String = taskViewModel.description
    val taskPriority = taskViewModel.taskPriority
    val dueDate: Long? = taskViewModel.dueDate
    val isCompleted = taskViewModel.isCompleted
    val isPinned = taskViewModel.isPinned
    val isPopAlarmSelected = taskViewModel.isPopAlarmSelected
    val date = taskViewModel.date

    val allCategories by taskViewModel.allCategories.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val mySecondBackgroundColor = Brush.myBackgroundBrush(radius = 6800f / 1.1f)
    val myActivatedColor = MyTheme.MyYellow
    val myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor
    val myContentColor = MaterialTheme.colorScheme.myContentColor
    val myTextColor = MaterialTheme.colorScheme.myTextColor

    BackHandler(true) {
        navigateToHomeScreen(CrudAction.NO_ACTION)
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
                        navigateToHomeScreen = { crudAction: CrudAction ->
                            if (crudAction == CrudAction.NO_ACTION) {
                                navigateToHomeScreen(crudAction)
                            } else {
                                if (taskViewModel.validateFields()) {
                                    navigateToHomeScreen(crudAction)
                                } else {
                                    SnackToastMessages.EMPTY_FIELDS.showToast(context)
                                }
                            }
                        },
                        scope = coroutineScope,
                        keyboardController = keyboardController,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )
                },
                bottomBar = {
                        BottomBarTaskScreen(
                            onNewCheckListClicked = { /*TODO*/ },
                            dueDate = dueDate,
                            onDueDateChange = { taskViewModel.updateDueDate(it) },
                            showToastInvalidDate = { SnackToastMessages.INVALID_TIME.showToast(context)  },
                            showToastPickADate = { SnackToastMessages.PICK_A_DATE.showToast(context)  },
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
                            myActivatedColor = myActivatedColor,
                            myBackgroundColor = myBackgroundColor,
                            myContentColor = myContentColor,
                            myTextColor = myTextColor
                        )
                },
                content = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(mySecondBackgroundColor),
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
                                myBackgroundColor = myBackgroundColor,
                                myContentColor = myContentColor,
                                myTextColor = myTextColor
                            )
                        }
                    )
                }
            )
        }
    )
}