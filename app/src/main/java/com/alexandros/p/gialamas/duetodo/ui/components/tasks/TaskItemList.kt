package com.alexandros.p.gialamas.duetodo.ui.components.tasks


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.components.BottomSearchBar
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_SMALL_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrBackgroundColor

@Composable
fun TaskItemList(
    taskTableList: List<TaskTable>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.topAppBarrBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(LARGE_PADDING), // TODO { paddings }

        content = {
            items(items = taskTableList, key = { task -> task.taskId }) { task ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(EXTRA_SMALL_PADDING),
                    content = {
                        TaskItem(
                            taskTable = task,
                            navigateToTaskScreen = navigateToTaskScreen
                        )
                    }
                )
            }
        }
    )
}