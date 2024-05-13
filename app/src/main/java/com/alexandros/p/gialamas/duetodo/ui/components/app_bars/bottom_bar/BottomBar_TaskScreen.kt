package com.alexandros.p.gialamas.duetodo.ui.components.app_bars.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.components.actions.ActionCheckList
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionPin
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionCategory
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionReminder
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task.ActionTaskPriority
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.MEDIUM_PADDING
import com.alexandros.p.gialamas.duetodo.util.SettingAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarTaskScreen(
    onNewCheckListClicked: () -> Unit,
    onRemindClicked: (settingAction: SettingAction) -> Unit,
    dueDate: Long?,
    onCategoryClicked: (settingAction: SettingAction) -> Unit,
    category: String,
    taskPriority: TaskPriority,
    onTaskPrioritySelected: (TaskPriority) -> Unit,
    onPinClicked: (settingAction: SettingAction) -> Unit,
    pin: Boolean,
    myActivatedColor: Color,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    val editedBackgroundColor = myBackgroundColor.copy(alpha = 0.9f)

    TopAppBar(
        modifier = Modifier
            .background(Color.Transparent)
            .height(40.dp),  // TODO { hardcode value }
        title = { Text(text = "") },
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
                        myActivatedColor = myActivatedColor,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )

                    Spacer(modifier = Modifier.padding(start = MEDIUM_PADDING))

                    ActionCategory(
                        onCategoryClicked = onCategoryClicked,
                        category = category,
                        myActivatedColor = myActivatedColor,
                        myBackgroundColor = myBackgroundColor,
                        myContentColor = myContentColor,
                        myTextColor = myTextColor
                    )

                    Spacer(modifier = Modifier.padding(start = EXTRA_LARGE_PADDING))

                    ActionTaskPriority(
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