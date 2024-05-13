package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.sort.TaskPriorityItem
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_PRIORITY_ITEM_INDICATOR_SIZE
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush

@Composable
fun ActionTaskPriority(
    taskPriority: TaskPriority,
    onTaskPrioritySelected: (TaskPriority) -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    var expanded by remember { mutableStateOf(false) }
    val dropDownBackgroundColor = Brush.myBackgroundBrush(radius = 900 / 1f)

    val dropDownMenuColor = Brush.myBackgroundBrush(radius = 1800 / 0.9f)

    Column(
        modifier = Modifier,
        content = {

            Canvas(
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .size(TASK_PRIORITY_ITEM_INDICATOR_SIZE),
                onDraw = { drawCircle(color = taskPriority.color) })

            DropdownMenu(
                modifier = Modifier
                    .clip(HOME_SCREEN_ROUNDED_CORNERS)
                    .background(dropDownBackgroundColor)
                    .border(
                        width = FIRST_BORDER_STROKE,
                        color = myContentColor.copy(alpha = LIGHT_BORDER_STROKE_ALPHA),
                        shape = HOME_SCREEN_ROUNDED_CORNERS
                    )
                    .border(
                        width = SECOND_BORDER_STROKE,
                        color = myBackgroundColor,
                        shape = HOME_SCREEN_ROUNDED_CORNERS
                    ),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    TaskPriority.entries.toTypedArray().slice(0..2).forEach { taskPriority ->

                        DropdownMenuItem(
                            text = {
                                TaskPriorityItem(
                                    taskPriority = taskPriority,
                                    myBackgroundColor = myBackgroundColor,
                                    myContentColor = myContentColor,
                                    myTextColor = myTextColor
                                )
                            },
                            onClick = {
                                expanded = false
                                onTaskPrioritySelected(taskPriority)
                            })

                    }
                }
            )
        }
    )
}