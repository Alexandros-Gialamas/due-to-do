package com.alexandros.p.gialamas.duetodo.ui.screens

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel,
    selectedTask: TaskTable?,
    navigateToHomeScreen: (CrudAction) -> Unit,
    context: Context,
//    onRemindClicked: (settingAction: Setting_Action) -> Unit,
//    onLabelClicked: (settingAction: Setting_Action) -> Unit,
) {


    val title = taskViewModel.title
    val category = taskViewModel.category
    val description: String = taskViewModel.description
    val taskPriority = taskViewModel.taskPriority
    val dueDate: Long? = taskViewModel.dueDate
    val isCompleted = taskViewModel.isCompleted
    val isPinned = taskViewModel.isPinned
    val isPopAlarmSelected = taskViewModel.isPopAlarmSelected

    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val mySecondBackgroundColor = Brush.myBackgroundBrush(radius = 6800f / 1.1f)
    val myActivatedColor = MyTheme.MyYellow
    val myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor
    val myContentColor = MaterialTheme.colorScheme.myContentColor
    val myTextColor = MaterialTheme.colorScheme.myTextColor

    BackHandler(true) {
        navigateToHomeScreen(CrudAction.NO_ACTION)
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
                                onRemindClicked = { },
                                category = category,
                                onCategoryClicked = {},
                                taskPriority = taskPriority,
                                onTaskPrioritySelected = { taskViewModel.updateTaskPriority(it) },
                                pin = isPinned,
                                onPinClicked = { settingAction : SettingAction->
                                               if (settingAction == SettingAction.SET) {
                                                   taskViewModel.updateIsPinned(true)
                                               } else if (settingAction == SettingAction.DISCARD){
                                                   taskViewModel.updateIsPinned(false)
                                               }
                                },
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