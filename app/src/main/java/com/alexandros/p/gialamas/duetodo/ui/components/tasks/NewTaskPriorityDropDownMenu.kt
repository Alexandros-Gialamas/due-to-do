package com.alexandros.p.gialamas.duetodo.ui.components.tasks

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.sort.TaskPriorityItem
import com.alexandros.p.gialamas.duetodo.ui.theme.DROP_DOWN_MENU_ICON_ANGLE_ANIMATION_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_PRIORITY_DROP_DOWN_MENU_HEIGHT
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_PRIORITY_ITEM_INDICATOR_SIZE
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun NewTaskPriorityDropDownMenu(
    taskPriority: TaskPriority,
    onTaskPrioritySelected: (TaskPriority) -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    var expanded by remember { mutableStateOf(false) }
    val angle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = stringResource(id = R.string.Angle_Animation_description_label)
    )

    val dropDownMenuColor = Brush.myBackgroundBrush(radius = 1800 / 0.9f)

    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = Modifier
            .clip(HOME_SCREEN_ROUNDED_CORNERS)
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(vertical = EXTRA_LARGE_PADDING)
            .onGloballyPositioned { parentSize = it.size }
            .height(TASK_PRIORITY_DROP_DOWN_MENU_HEIGHT)
            .clickable { expanded = !expanded }
            .border(
                width = FIRST_BORDER_STROKE,
                color = myBackgroundColor,
                shape = HOME_SCREEN_ROUNDED_CORNERS
            ),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Canvas(
                modifier = Modifier
                    .size(TASK_PRIORITY_ITEM_INDICATOR_SIZE)
                    .weight(1f),
                onDraw = { drawCircle(color = taskPriority.color) })
            Text(
                modifier = Modifier.weight(8f),
                text = taskPriority.name,
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(
                modifier = Modifier
                    .alpha(DROP_DOWN_MENU_ICON_ANGLE_ANIMATION_ALPHA)  // TODO { check this }
                    .rotate(degrees = angle)
                    .weight(1.5f),
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = stringResource(
                        id = R.string.Arrow_Drop_Down_Menu_Icon_description
                    )
                )
            }

            DropdownMenu(  // TODO { items not centered }
                modifier = Modifier
                    .clip(HOME_SCREEN_ROUNDED_CORNERS)
                    .width(with(LocalDensity.current) { parentSize.width.toDp() })
                    .background(dropDownMenuColor)
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
                onDismissRequest = { expanded = false })
            {

                TaskPriority.entries.toTypedArray().slice(0..2).forEach { taskPriority ->

                    DropdownMenuItem(
                        text = {
                            TaskPriorityItem( // TODO { resize Text }
                                taskPriority = taskPriority,
                                myBackgroundColor = myBackgroundColor,
                                myContentColor = myContentColor,
                                myTextColor = myTextColor
                            )
                        },
                        onClick = {
                            expanded = false
                            onTaskPrioritySelected(taskPriority)
                        }
                    )

                }
//                DropdownMenuItem(
//                    text = { TaskPriorityItem(taskPriority = TaskPriority.LOW) }, // TODO { resize Text }
//                    onClick = {
//                        expanded = false
//                        onTaskPrioritySelected(TaskPriority.LOW)
//                    }
//                )
//                DropdownMenuItem(
//                    text = { TaskPriorityItem(taskPriority = TaskPriority.MEDIUM) },
//                    onClick = {
//                        expanded = false
//                        onTaskPrioritySelected(TaskPriority.MEDIUM)
//                    }
//                )
//                DropdownMenuItem(
//                    text = { TaskPriorityItem(taskPriority = TaskPriority.HIGH) },
//                    onClick = {
//                        expanded = false
//                        onTaskPrioritySelected(TaskPriority.HIGH)
//                    }
//                )
            }
        }
    )
}

@Composable
@Preview
fun NewTaskPriorityDropDownMenuPreview() {
    NewTaskPriorityDropDownMenu(
        taskPriority = TaskPriority.HIGH,
        onTaskPrioritySelected = {},
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        myTextColor = MaterialTheme.colorScheme.myTextColor
    )
}
