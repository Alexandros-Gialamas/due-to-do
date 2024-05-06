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
import androidx.compose.ui.Modifier
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.components.tasks.DisplayTask
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.EditOrNewTopBar
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.Action
import com.alexandros.p.gialamas.duetodo.util.SnackToastMessages

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel,
    selectedTask: TaskTable?,
    navigateToHomeScreen: (Action) -> Unit,
    context: Context
) {
    val title: String = taskViewModel.title
    val description: String = taskViewModel.description
    val taskPriority: TaskPriority = taskViewModel.taskPriority
    val dueDate: Long? = taskViewModel.dueDate
    val isCompleted: Boolean = taskViewModel.isCompleted
    val isPopAlarmSelected: Boolean = taskViewModel.isPopAlarmSelected

    val myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor
    val myContentColor = MaterialTheme.colorScheme.myContentColor
    val myTextColor = MaterialTheme.colorScheme.myTextColor

    BackHandler(true) {
        navigateToHomeScreen(Action.NO_ACTION)
    }

    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(HOME_SCREEN_ROUNDED_CORNERS),
        content = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                containerColor = myBackgroundColor,
                contentColor = myContentColor,
                topBar = {
                    EditOrNewTopBar(
                        selectedTask = selectedTask,
                        navigateToHomeScreen = { action: Action ->
                            if (action == Action.NO_ACTION) {
                                navigateToHomeScreen(action)
                            } else {
                                if (taskViewModel.validateFields()) {
                                    navigateToHomeScreen(action)
                                } else {
                                    SnackToastMessages.EMPTY_FIELDS.showToast(context)
                                }
                            }
                        },
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )
                },
                bottomBar = {},
                content = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(myBackgroundColor),
                        content = {
                            DisplayTask(
                                title = title,
                                onTitleChange = { taskViewModel.updateTitle(it) },
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