package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home.sort

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.DateRange
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_PRIORITY_ITEM_INDICATOR_SIZE
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush

@Composable
fun ActionSort(
    onSortClicked: (taskPriority: TaskPriority) -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    var expanded by remember { mutableStateOf(false) }

    val dropDownBackgroundColor = Brush.myBackgroundBrush(radius = 1100 / 1f)

    IconButton(
        onClick = { expanded = !expanded },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_tasks),
            contentDescription = stringResource(id = R.string.BottomBar_Action_Sort_Description),
            tint = myContentColor
        )

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

                Row(
                    modifier = Modifier,
                    content = {

                        Column(
                            modifier = Modifier
                                .weight(1f),
                            content = {

                                DropdownMenuItem(
                                    text = {
                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp),
                                            verticalArrangement = Arrangement.SpaceEvenly,
                                            content = {

                                                IconButton(onClick = { onSortClicked(TaskPriority.HIGH) }) {
                                                    Canvas(
                                                        modifier = Modifier
                                                            .size(TASK_PRIORITY_ITEM_INDICATOR_SIZE),
                                                        onDraw = {
                                                            drawCircle(color = TaskPriority.HIGH.color)
                                                        }
                                                    )
                                                }

                                                IconButton(onClick = { onSortClicked(TaskPriority.LOW) }) {
                                                    Canvas(
                                                        modifier = Modifier
                                                            .size(TASK_PRIORITY_ITEM_INDICATOR_SIZE),
                                                        onDraw = {
                                                            drawCircle(color = TaskPriority.LOW.color)
                                                        }
                                                    )
                                                }

                                                IconButton(onClick = { onSortClicked(TaskPriority.NONE) }) {
                                                    Canvas(
                                                        modifier = Modifier
                                                            .size(TASK_PRIORITY_ITEM_INDICATOR_SIZE),
                                                        onDraw = {
                                                            drawCircle(color = TaskPriority.NONE.color)
                                                        }
                                                    )
                                                }
                                            }
                                        )


                                    },
                                    leadingIcon = {
                                        Column(
                                            content = {
                                                IconButton(
                                                    onClick = { /*TODO*/ }
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .offset(x = (6).dp),
                                                        content = {
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.ic_sort_by_az),
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = myContentColor
                                                            )
                                                            Icon(
                                                                modifier = Modifier
                                                                    .size(
                                                                        height = 20.dp,
                                                                        width = 24.dp
                                                                    )
                                                                    .offset(x = (-13).dp, y = 4.dp)
                                                                    .rotate(180f),
                                                                painter = painterResource(id = R.drawable.ic_straight_up),
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = myContentColor
                                                            )
                                                        }
                                                    )
                                                }

                                                IconButton(
                                                    onClick = { /*TODO*/ }
                                                ) {
                                                    Box(
                                                        modifier = Modifier,
                                                        content = {
                                                            Icon(
                                                                modifier = Modifier
                                                                    .size(
                                                                        height = 20.dp,
                                                                        width = 24.dp
                                                                    )
                                                                    .offset(x = (13).dp, y = 4.dp)
                                                                    .rotate(180f),
                                                                painter = painterResource(id = R.drawable.ic_straight_up),
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = myContentColor
                                                            )
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.ic_sort_by_az),
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = myContentColor
                                                            )
                                                        }
                                                    )
                                                }

                                                IconButton(
                                                    onClick = { /*TODO*/ }
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .offset(x = (8).dp),
                                                        content = {
                                                            Icon(
                                                                modifier = Modifier
                                                                    .size(28.dp),
                                                                painter = painterResource(id = R.drawable.ic_sort_by_az),
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = myContentColor
                                                            )
                                                            Icon(
                                                                modifier = Modifier
                                                                    .size(
                                                                        height = 42.dp,
                                                                        width = 42.dp
                                                                    )
                                                                    .offset(
                                                                        x = (-6).dp,
                                                                        y = (-4).dp
                                                                    ),
                                                                imageVector = Icons.Filled.Close,
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = Color.Red
                                                            )
                                                        }
                                                    )
                                                }
                                            }
                                        )
                                    },
                                    trailingIcon = {
                                        Column(
                                            content = {
                                                IconButton(
                                                    onClick = { /*TODO*/ }
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .offset(x = (2).dp),
                                                        content = {
                                                            Icon(
                                                                imageVector = Icons.Outlined.DateRange,
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = myContentColor
                                                            )
                                                            Icon(
                                                                modifier = Modifier
                                                                    .size(
                                                                        height = 28.dp,
                                                                        width = 24.dp
                                                                    )
                                                                    .offset(x = (-13).dp, y = 4.dp)
                                                                    .rotate(180f),
                                                                painter = painterResource(id = R.drawable.ic_straight_up),
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = myContentColor
                                                            )
                                                        }
                                                    )
                                                }

                                                IconButton(
                                                    onClick = { /*TODO*/ }
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .offset(x = 2.dp),
                                                        content = {
                                                            Icon(
                                                                modifier = Modifier
                                                                    .size(
                                                                        height = 28.dp,
                                                                        width = 24.dp
                                                                    )
                                                                    .offset(x = (13).dp, y = (-4).dp),
                                                                painter = painterResource(id = R.drawable.ic_straight_up),
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = myContentColor
                                                            )
                                                            Icon(
                                                                imageVector = Icons.Outlined.DateRange,
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = myContentColor
                                                            )
                                                        }
                                                    )
                                                }

                                                IconButton(
                                                    onClick = { /*TODO*/ }
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .offset(x = (8).dp),
                                                        content = {
                                                            Icon(
                                                                modifier = Modifier
                                                                    .size(28.dp),
                                                                imageVector = Icons.Outlined.DateRange,
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = myContentColor
                                                            )
                                                            Icon(
                                                                modifier = Modifier
                                                                    .size(
                                                                        height = 42.dp,
                                                                        width = 42.dp
                                                                    )
                                                                    .offset(
                                                                        x = (-6).dp,
                                                                        y = (-4).dp
                                                                    ),
                                                                imageVector = Icons.Filled.Close,
                                                                contentDescription = stringResource(
                                                                    id = R.string.Reminder_Task_Icon_Description
                                                                ),
                                                                tint = Color.Red
                                                            )
                                                        }
                                                    )
                                                }
                                            }
                                        )
                                    },
                                    onClick = { /*TODO*/ }
                                )
                            }
                        )
                    }
                )
            }
        )
    }

}

//@Composable
//@Preview
//private fun ActionSortPreview() {
//    ActionSort(
//        onSortClicked = {},
//        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
//        myContentColor = MaterialTheme.colorScheme.myContentColor,
//        myTextColor = MaterialTheme.colorScheme.myTextColor
//    )
//}