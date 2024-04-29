package com.alexandros.p.gialamas.duetodo.ui.components.tasks

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable

@Composable
fun TaskList(
    taskTableList : List<TaskTable>,
    navigateToTaskScreen : (taskId : Int) -> Unit
){
    MaterialTheme (
        content = {
            LazyColumn(
                modifier = Modifier,
                content = {

                    items(items = taskTableList, key = {task -> task.taskId}){task ->
                        TaskItem(
                            taskTable = task,
                            navigateToTaskScreen = navigateToTaskScreen
                            )
                    }
                }
            )








        }
    )
}