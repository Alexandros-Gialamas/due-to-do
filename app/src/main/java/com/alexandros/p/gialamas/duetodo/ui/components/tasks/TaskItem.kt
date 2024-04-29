package com.alexandros.p.gialamas.duetodo.ui.components.tasks

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.MEDIUM_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.ONE_TAB_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_ITEM_SHADOW_ELEVATION
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_ITEM_TONAL_ELEVATION
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_PRIORITY_ITEM_INDICATOR_SIZE
import com.alexandros.p.gialamas.duetodo.ui.theme.TINY_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.taskItemTextColor

@Composable
fun TaskItem(
    taskTable: TaskTable,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    MaterialTheme(
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.Transparent,
                tonalElevation = TASK_ITEM_TONAL_ELEVATION,
                shadowElevation = TASK_ITEM_SHADOW_ELEVATION,
                onClick = { navigateToTaskScreen(taskTable.taskId) },
                content = {
                    MaterialTheme(
                        content = {
                            Row(
                                modifier = Modifier
                                    .padding(EXTRA_LARGE_PADDING)
                                    .fillMaxWidth(),
                                content = {
                                    Column(
                                        modifier = Modifier
                                            .weight(14f),
                                        content = {
                                            Text(
                                                modifier = Modifier
                                                    .padding(
                                                        start = LARGE_PADDING),
                                                text = taskTable.title,
                                                color = colorScheme.taskItemTextColor,
                                                style = typography.titleLarge,
                                                fontWeight = FontWeight.SemiBold,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )

                                            Text(
                                                modifier = Modifier
                                                    .padding(
                                                        start = ONE_TAB_PADDING,
                                                        bottom = TINY_PADDING)
                                                    .fillMaxWidth(),
                                                text = taskTable.description,
                                                color = colorScheme.taskItemTextColor,
                                                style = typography.bodyMedium,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )

                                        }
                                    )

                                    Box(
                                        modifier = Modifier
                                            .padding(end = MEDIUM_PADDING)
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .align(Alignment.CenterVertically),
                                        contentAlignment = Alignment.CenterEnd,
                                        content = {
                                            Canvas(
                                                modifier = Modifier
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
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(taskTable = TaskTable(
        taskId = 0,
        title = "Go to Gym",
        description = "Tomorrow at 5 pm",
        taskPriority = TaskPriority.MEDIUM
    ),
        navigateToTaskScreen = {}
    )
}