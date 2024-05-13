package com.alexandros.p.gialamas.duetodo.ui.components.tasks.homescreen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.theme.DIALOG_BUTTON_SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.MEDIUM_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_ITEM_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_ITEM_SHADOW_ELEVATION
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_ITEM_TONAL_ELEVATION
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_PRIORITY_ITEM_INDICATOR_SIZE
import com.alexandros.p.gialamas.duetodo.ui.theme.TINY_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TaskItem(
    taskTable: TaskTable,
//    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    isGridLayout: Boolean,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    val surfaceBackgroundColor = Brush.myBackgroundBrush(radius = 1600f / 1.1f)



    Surface(
        modifier = Modifier
            .clip(TASK_ITEM_ROUNDED_CORNERS)
            .width(intrinsicSize = IntrinsicSize.Max)
            .height(intrinsicSize = IntrinsicSize.Min)
            .background(myBackgroundColor)
//        .background(MaterialTheme.colorScheme.primary)  // TODO { try colors }
//        .background(MyTheme.SecondGreen)
//        .padding(vertical = 8.dp, horizontal = 24.dp)
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
                    DIALOG_BUTTON_SECOND_BORDER_STROKE,
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
                        modifier = Modifier
                            .padding(EXTRA_LARGE_PADDING),
                        content = {
                            Column(
                                modifier = Modifier
                                    .weight(14f),
                                content = {
                                    Text(
                                        modifier = Modifier,
                                        text = taskTable.title,
                                        color = colorScheme.myTextColor,
                                        style = typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = if (isGridLayout) 2 else 1,
                                        softWrap = true,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Spacer(modifier = Modifier.height(MEDIUM_PADDING))

                                    Text(
                                        modifier = Modifier
                                            .padding(bottom = TINY_PADDING)
                                            .fillMaxWidth(),
                                        text = taskTable.description,
                                        color = colorScheme.myTextColor,
                                        style = typography.bodyMedium,
                                        maxLines = if (isGridLayout) 4 else 2,
                                        softWrap = true,
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


@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(
        taskTable = TaskTable(
            taskId = 0,
            title = "Go to Gym",
            description = "Tomorrow at 5 pm i will expect to be at my cardio exercises",
            taskPriority = TaskPriority.MEDIUM
        ),
        navigateToTaskScreen = {},
        myBackgroundColor = colorScheme.myBackgroundColor,
        myContentColor = colorScheme.myContentColor,
        myTextColor = colorScheme.myTextColor,
        isGridLayout = false
    )
}
