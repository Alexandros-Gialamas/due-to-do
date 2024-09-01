package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_PRIORITY_ITEM_INDICATOR_SIZE
import com.alexandros.p.gialamas.duetodo.ui.theme.myActivatedColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun ActionTaskPriorityMenu(
    modifier: Modifier = Modifier,
    taskPriority: TaskPriority,
    onTaskPrioritySelected: (TaskPriority) -> Unit,
    myBackgroundColor: Color = MaterialTheme.colorScheme.myBackgroundColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor
) {


    var expanded by remember { mutableStateOf(false) }
    val dropDownBackgroundColor = Brush.myBackgroundBrush(radius = 900 / 1f)


    Column(
        modifier = modifier,
        content = {

            Canvas(
                modifier = modifier
                    .clickable { expanded = !expanded }
                    .size(TASK_PRIORITY_ITEM_INDICATOR_SIZE),
                onDraw = { drawCircle(color = taskPriority.color) })

            DropdownMenu(
                modifier = modifier
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
                offset = DpOffset(x = (-60).dp, y = (0).dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    TaskPriority.entries.toTypedArray().slice(0..2).forEach { taskPriority ->

                        DropdownMenuItem(
                            modifier = modifier
                                .fillMaxWidth(),
                            leadingIcon = {
                                Canvas(
                                    modifier = modifier
                                        .padding(start = LARGE_PADDING)
                                        .size(TASK_PRIORITY_ITEM_INDICATOR_SIZE),
                                    onDraw = {
                                        drawCircle(color = taskPriority.color)
                                    }
                                )
                            },
                            text = {
                                    Text(
                                        modifier = modifier
                                            .padding(end = LARGE_PADDING),
                                        text =
                                        taskPriority.name,
                                        color = myTextColor,
                                        style = MaterialTheme.typography.bodySmall,
                                        softWrap = true,
                                        textAlign = TextAlign.Start
                                    )
                            },
                            onClick = {
                                expanded = false
                                onTaskPrioritySelected(taskPriority)
                            },
                        )
                    }
                }
            )
        }
    )
}

