package com.alexandros.p.gialamas.duetodo.ui.components.app_bars.bottom_bar

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.components.actions.ActionCheckList
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionCategory
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionPin
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionReminder
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionTaskPriorityMenu
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.MEDIUM_PADDING
import com.alexandros.p.gialamas.duetodo.util.RepeatFrequency
import com.alexandros.p.gialamas.duetodo.util.RequestState
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarTaskScreen(
    onNewCheckListClicked: () -> Unit,
    dueDate : Long?,
    onDueDateChange : (Long?) -> Unit,
    onRemindClicked: () -> Unit,
    category: String,
    taskCategoryList: RequestState<List<TaskCategoryTable>>,
    scope : CoroutineScope,
    keyboardController : SoftwareKeyboardController?,
    onCategoryClicked: (selectedCategory: String) -> Unit,
    taskPriority: TaskPriority,
    onTaskPrioritySelected: (TaskPriority) -> Unit,
    pin: Boolean,
    onPinClicked: () -> Unit,
    showToastPickADate: () -> Unit,
    showToastInvalidDate: () -> Unit,
    onRepeatFrequencyChanged: (RepeatFrequency) -> Unit,
    context : Context,
    myActivatedColor: Color,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    val editedBackgroundColor = myBackgroundColor.copy(alpha = 0.9f)

    TopAppBar(
        modifier = Modifier
            .background(Color.Transparent)
            .height(Dp.Unspecified),  // TODO { changed to higher bar }
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(editedBackgroundColor),
        navigationIcon = {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {

                    Spacer(modifier = Modifier.padding(start = MEDIUM_PADDING))

                    ActionCheckList(
                        onNewCheckListClicked = onNewCheckListClicked,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )

                    Spacer(modifier = Modifier.padding(start = MEDIUM_PADDING))

                    ActionReminder(
                        onRemindClicked = onRemindClicked,
                        dueDate = dueDate,
                        onDueDateChanged = onDueDateChange,
                        showToastPickADate = showToastPickADate,
                        showToastInvalidDate = showToastInvalidDate,
                        onRepeatFrequencyChanged = onRepeatFrequencyChanged,
                        context = context,
                        myActivatedColor = myActivatedColor,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )

                    Spacer(modifier = Modifier.padding(start = MEDIUM_PADDING))

                    if (taskCategoryList is RequestState.Success) {
                        ActionCategory(
                            category = category,
                            onCategoryClicked = onCategoryClicked,
                            taskCategoryList = taskCategoryList.data,
                            scope = scope,
                            keyboardController = keyboardController,
                            myActivatedColor = myActivatedColor,
                            myBackgroundColor = myBackgroundColor,
                            myContentColor = myContentColor,
                            myTextColor = myTextColor
                        )
                    }

                    Spacer(modifier = Modifier.padding(start = EXTRA_LARGE_PADDING))

                    ActionTaskPriorityMenu(
                        taskPriority = taskPriority,
                        onTaskPrioritySelected = onTaskPrioritySelected,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )

                    Spacer(modifier = Modifier.padding(start = EXTRA_LARGE_PADDING))

                    ActionPin(
                        onPinClicked = onPinClicked,
                        pin = pin,
                        myActivatedColor = myActivatedColor,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )

                }
            )
        },
        actions = {}
    )
}