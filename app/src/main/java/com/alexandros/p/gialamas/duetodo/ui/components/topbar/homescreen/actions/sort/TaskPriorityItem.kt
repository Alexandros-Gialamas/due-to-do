package com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.sort

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.TASK_PRIORITY_ITEM_INDICATOR_SIZE
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun TaskPriorityItem(
    taskPriority: TaskPriority,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {


    Row(
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Canvas(
                modifier = Modifier
                    .size(TASK_PRIORITY_ITEM_INDICATOR_SIZE),
                onDraw = {
                    drawCircle(color = taskPriority.color)
                })
            Text(
                modifier = Modifier
                    .padding(start = LARGE_PADDING),
                text = taskPriority.name,
                style = typography.bodyMedium,
                color = myTextColor // TODO { revisit }
            )
        }
    )
}

@Composable
@Preview
private fun TaskPriorityItemPreview() {
    TaskPriorityItem(
        taskPriority = TaskPriority.MEDIUM,
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        myTextColor = MaterialTheme.colorScheme.myTextColor
    )
}