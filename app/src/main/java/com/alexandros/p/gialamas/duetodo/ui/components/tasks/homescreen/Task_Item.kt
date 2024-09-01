package com.alexandros.p.gialamas.duetodo.ui.components.tasks.homescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_SMALL_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.MEDIUM_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.SMALL_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_ITEM_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_ITEM_SHADOW_ELEVATION
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_ITEM_TONAL_ELEVATION
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_PRIORITY_ITEM_INDICATOR_SIZE
import com.alexandros.p.gialamas.duetodo.ui.theme.TINY_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myCheckBoxColors
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    taskTable: TaskTable,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    isGridLayout: Boolean,
    onCheckedChange: () -> Unit,
    myBackgroundColor: Color = MaterialTheme.colorScheme.myBackgroundColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor
) {

    val surfaceBackgroundColor = Brush.myBackgroundBrush(radius = 1600f / 1.1f)
    val countIsCompleted = taskTable.checkListItem.count { it.isCompleted }

    Surface(
        modifier = modifier
            .clip(TASK_ITEM_ROUNDED_CORNERS)
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min)
            .background(myBackgroundColor)
            .border(
                BorderStroke(
                    FIRST_BORDER_STROKE,
                    color =
                    if (isGridLayout) taskTable.taskPriority.color else myContentColor.copy(alpha = LIGHT_BORDER_STROKE_ALPHA)
                ),
                shape = TASK_ITEM_ROUNDED_CORNERS
            )
            .border(
                BorderStroke(
                    SECOND_BORDER_STROKE,
                    brush =
                    surfaceBackgroundColor
                ),
                shape = TASK_ITEM_ROUNDED_CORNERS
            ),
        color = Color.Transparent,
        tonalElevation = TASK_ITEM_TONAL_ELEVATION,
        shadowElevation = TASK_ITEM_SHADOW_ELEVATION,
        onClick = { navigateToTaskScreen(taskTable.taskId) },
        content = {
            MaterialTheme(
                content = {
                    Row(
                        modifier = modifier
                            .padding(EXTRA_LARGE_PADDING),
                        content = {
                            Column(
                                modifier = Modifier
                                    .weight(12f),
                                content = {

                                    ShowTaskItemTitle(
                                        taskTable = taskTable,
                                        isGridLayout = isGridLayout
                                    )

                                    Spacer(modifier = Modifier.height(MEDIUM_PADDING))

                                    if (taskTable.isChecklist) {

                                        ShowCheckListItem(
                                            taskTable = taskTable,
                                            onCheckedChange = onCheckedChange
                                        )

                                        ShowMoreItemsText(taskTable = taskTable)

                                        ShowCheckedItemsText(
                                            taskTable = taskTable,
                                            countIsCompleted = countIsCompleted
                                        )
                                    } else {
                                        ShowTaskItem(
                                            taskTable = taskTable,
                                            isGridLayout = isGridLayout
                                        )
                                    }

                                    if (taskTable.dueDate != null) {
                                        ShowReminderText(taskTable = taskTable)
                                    }
                                }
                            )

                            Column(
                                modifier = modifier,
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.End,
                                content = {
                                    if (!isGridLayout) {
                                        ShowPriorityIndicatorIcon(taskTable = taskTable)
                                    }
                                    if (taskTable.isPinned) {
                                        ShowPinnedIndicatorIcon()
                                    }
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun ShowTaskItemTitle(
    modifier: Modifier = Modifier,
    taskTable: TaskTable,
    isGridLayout: Boolean
) {
    Text(
        modifier = modifier
            .padding(horizontal = MEDIUM_PADDING),
        text = taskTable.title,
        color = colorScheme.myTextColor,
        style = typography.titleMedium,
        fontWeight = FontWeight.Bold,
        maxLines = if (isGridLayout) 2 else 1,
        softWrap = true,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun ShowTaskItem(
    modifier: Modifier = Modifier,
    taskTable: TaskTable,
    isGridLayout: Boolean
) {
    Text(
        modifier = modifier
            .padding(
                horizontal = LARGE_PADDING,
                vertical = TINY_PADDING
            )
            .fillMaxWidth(),
        text = taskTable.description,
        color = colorScheme.myTextColor,
        style = typography.bodyMedium,
        fontWeight = FontWeight.ExtraLight,
        maxLines = if (isGridLayout) 4 else 2,
        softWrap = true,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
private fun ShowCheckListItem(
    modifier: Modifier = Modifier,
    taskTable: TaskTable,
    onCheckedChange: () -> Unit
) {
    taskTable.checkListItem.filter { !it.isCompleted }.take(4)
        ?.forEach { checkListTask ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Box(
                        modifier = modifier,
                        contentAlignment = Alignment.CenterStart,
                        content = {
                            Checkbox(
                                modifier = modifier
                                    .size(MEDIUM_PADDING),
                                checked = checkListTask.isCompleted,
                                onCheckedChange = { onCheckedChange() },
                                colors = CheckboxDefaults.myCheckBoxColors
                            )
                        }
                    )
                    Box(
                        modifier = modifier
                            .weight(3f),
                        contentAlignment = Alignment.CenterEnd,
                        content = {
                            Text(
                                modifier = modifier
                                    .padding(
                                        horizontal = EXTRA_LARGE_PADDING,
                                        vertical = TINY_PADDING
                                    )
                                    .fillMaxWidth(),
                                text = checkListTask.listItemDescription,
                                color = colorScheme.myTextColor,
                                style = typography.bodyMedium,
                                fontWeight = FontWeight.ExtraLight,
                                maxLines = 2,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            )
        }
}

@Composable
private fun ShowMoreItemsText(
    modifier: Modifier = Modifier,
    taskTable: TaskTable,
    myFadedTextColor: Color = MaterialTheme.colorScheme.myTextColor.copy(alpha = 0.7f)
) {
    if (taskTable.isChecklist && taskTable.checkListItem.size > 4) {
        val countItems = taskTable.checkListItem.size - 4
        Text(
            modifier = modifier
                .padding(start = LARGE_PADDING),
            text = if (countItems > 1)
                "$countItems ${stringResource(id = R.string.CheckList_More_Items_Count)}" else
                "$countItems ${stringResource(id = R.string.CheckList_More_Item_Count)}",
            style = typography.bodySmall.copy(fontSize = 10.sp),
            fontWeight = FontWeight.ExtraLight,
            softWrap = true,
            overflow = TextOverflow.Ellipsis,
            color = myFadedTextColor
        )
    }
}

@Composable
private fun ShowCheckedItemsText(
    modifier: Modifier = Modifier,
    taskTable: TaskTable,
    countIsCompleted: Int,
    myFadedTextColor: Color = MaterialTheme.colorScheme.myTextColor.copy(alpha = 0.7f)
) {
    if (taskTable.isChecklist && countIsCompleted > 0) {
        Text(
            modifier = modifier
                .padding(start = LARGE_PADDING),
            text = if (countIsCompleted > 1)
                "$countIsCompleted ${stringResource(id = R.string.CheckList_Checked_Items_Count)}" else
                "$countIsCompleted ${stringResource(id = R.string.CheckList_Checked_Item_Count)}",
            style = typography.bodySmall.copy(fontSize = 10.sp),
            fontWeight = FontWeight.ExtraLight,
            softWrap = true,
            overflow = TextOverflow.Ellipsis,
            color = myFadedTextColor
        )
    }
}

@Composable
private fun ShowPriorityIndicatorIcon(
    modifier: Modifier = Modifier,
    taskTable: TaskTable,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd,
        content = {
            Canvas(
                modifier = modifier
                    .size(TASK_PRIORITY_ITEM_INDICATOR_SIZE),
                onDraw = {
                    drawCircle(
                        color = taskTable.taskPriority.color
                    )
                }
            )
        }
    )
}

@Composable
private fun ShowReminderText(
    modifier: Modifier = Modifier,
    taskTable: TaskTable,
    myFadedBackgroundColor: Color = MyTheme.MyCharcoal.copy(alpha = 0.2f),
    myFadedContentColor: Color = MaterialTheme.colorScheme.myContentColor.copy(alpha = 0.9f),
    myFadedTextColor: Color = MaterialTheme.colorScheme.myTextColor.copy(alpha = 0.9f)
) {
    Box(
        modifier = modifier
            .padding(start = SMALL_PADDING)
            .background(color = myFadedBackgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(EXTRA_SMALL_PADDING),
        contentAlignment = Alignment.BottomStart,
        content = {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                content = {

                    Icon(
                        modifier = modifier
                            .size(16.dp),
                        painter = if (taskTable.reScheduleDate != null)
                            painterResource(id = R.drawable.ic_repeat) else
                            painterResource(id = R.drawable.ic_alarm),
                        contentDescription = null,  // TODO { description }
                        tint = myFadedContentColor
                    )

                    Text(
                        modifier = modifier
                            .padding(start = SMALL_PADDING),
                        text = "${taskTable.dueDate?.let { formatDate(it) }}",
                        style = typography.bodySmall.copy(fontSize = 10.sp),
                        fontWeight = FontWeight.ExtraLight,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        color = myFadedTextColor
                    )
                }
            )
        }
    )
}

private fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("MMM, d", Locale.getDefault())
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    val date = Date(timestamp)
    val formattedDate = dateFormat.format(date)
    val formattedTime = timeFormat.format(date)
    val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(date)

    return if (year.toInt() != currentYear) {
        "$formattedDate, $year, $formattedTime"
    } else {
        "$formattedDate, $formattedTime"
    }
}

@Composable
private fun ShowPinnedIndicatorIcon(
    modifier: Modifier = Modifier,
    myFadedContentColor: Color = MaterialTheme.colorScheme.myContentColor.copy(alpha = 0.7f)
) {
    Box(
        modifier = modifier
            .fillMaxHeight(),
        contentAlignment = Alignment.BottomEnd,
        content = {
            Icon(
                modifier = modifier
                    .size(16.dp)
                    .rotate(45f),
                painter = painterResource(id = R.drawable.ic_pinned),
                contentDescription = null,  // TODO { description }
                tint = myFadedContentColor
            )
        }
    )
}


@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(
        taskTable = TaskTable(
            taskId = 0,
            title = "Go to Gym",
            description = "Tomorrow at 5 pm i will expect to be at my cardio exercises",
            taskPriority = TaskPriority.MEDIUM,
            checkListItem = emptyList()
        ),
        navigateToTaskScreen = {},
        myBackgroundColor = colorScheme.myBackgroundColor,
        myContentColor = colorScheme.myContentColor,
        isGridLayout = false,
        onCheckedChange = {}
    )
}
