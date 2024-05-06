package com.alexandros.p.gialamas.duetodo.ui.screens

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.components.tasks.DisplayTask
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.EditOrNewTopBar
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrContentColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.Action
import com.alexandros.p.gialamas.duetodo.util.BackHandlerInterception
import com.alexandros.p.gialamas.duetodo.util.Constants.MAX_TASK_TITLE_LENGTH
import com.alexandros.p.gialamas.duetodo.util.SnackToastMessages

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel,
    selectedTask: TaskTable?,
    navigateToHomeScreen: (Action) -> Unit,
    context: Context
) {
    val title: String by taskViewModel.title
    val description: String by taskViewModel.description
    val taskPriority: TaskPriority by taskViewModel.taskPriority
    val dueDate: Long? by taskViewModel.dueDate
    val isCompleted: Boolean by taskViewModel.isCompleted
    val isPopAlarmSelected: Boolean by taskViewModel.isPopAlarmSelected

    BackHandler(true) {
        navigateToHomeScreen(Action.NO_ACTION)
    }

    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(HOME_SCREEN_ROUNDED_CORNERS),
        content = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                containerColor = colorScheme.topAppBarrBackgroundColor,
                contentColor = colorScheme.topAppBarrContentColor,
                topBar = {
                    EditOrNewTopBar(
                        selectedTask = selectedTask,
                        navigateToHomeScreen = { action: Action ->
                            if (action == Action.NO_ACTION) { navigateToHomeScreen(action)
                            }else {
                                if (taskViewModel.validateFields()) {
                                    navigateToHomeScreen(action)
                                } else {
                                    SnackToastMessages.EMPTY_FIELDS.showToast(context)
                                }
                            }
                        }
                    )
                },
                bottomBar = {},
                content = { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding),
                        content = {
                            DisplayTask(
                                title = title,
                                onTitleChange = { taskViewModel.updateTitle(it) },
                                description = description,
                                onDescriptionChange = { taskViewModel.description.value = it },
                                taskPriority = taskPriority,
                                onTaskPriorityChange = { taskViewModel.taskPriority.value = it }
                            )
                        }
                    )
                }
            )
        }
    )
}