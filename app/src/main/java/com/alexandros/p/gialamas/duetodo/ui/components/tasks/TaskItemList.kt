package com.alexandros.p.gialamas.duetodo.ui.components.tasks


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.components.RedBackGround
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_SMALL_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.Action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItemList(
    taskTableList: List<TaskTable> = listOf(),
    onSwipeToDelete : (Action, TaskTable) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
//    searchedText : String
) {

//    val filteredTasks = remember(taskTableList) {11
//        if (searchedText.isNotBlank()) {
//            taskTableList.sortedBy { it.taskId }.filter { item ->
//                item.title.contains(searchedText, ignoreCase = true) ||
//                        item.description.contains(searchedText, ignoreCase = true)
//            }
//        }
//    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.topAppBarrBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(LARGE_PADDING), // TODO { paddings }

        content = {
            items(items = taskTableList, key = { task -> task.taskId }) { task ->

                val dismissState = rememberDismissState()
                val dismissDirection = dismissState.dismissDirection
                val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
                if (isDismissed && dismissDirection == DismissDirection.EndToStart){
                    onSwipeToDelete(Action.DELETE, task)
                }
                val degrees by animateFloatAsState(
                    targetValue =
                        if (dismissState.targetValue == DismissValue.Default)
                            0f
                        else
                            -45f,
                    label = "Swipe for Delete"
                )

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = { RedBackGround(degrees = degrees) }, // TODO { revisit color }
                    dismissContent = {
                        Box(
                            modifier = Modifier
                                .clip(HOME_SCREEN_ROUNDED_CORNERS)
                                .fillMaxWidth(0.9f)
                                .background(MaterialTheme.colorScheme.topAppBarrBackgroundColor)
                                .padding(EXTRA_SMALL_PADDING),
                            content = {
                                TaskItem(
                                    taskTable = task,
                                    navigateToTaskScreen = navigateToTaskScreen,
                                )
                            }
                        )
                    }
                )


            }
        }
    )
}