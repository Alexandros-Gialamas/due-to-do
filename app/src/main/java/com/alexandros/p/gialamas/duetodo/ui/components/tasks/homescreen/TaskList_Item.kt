package com.alexandros.p.gialamas.duetodo.ui.components.tasks.homescreen


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_SMALL_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction

//@SuppressLint("CoroutineCreationDuringComposition")

@Composable
fun TaskListItem(
    modifier: Modifier = Modifier,
    taskTableList: List<TaskTable> = listOf(),
    isCheckList: Boolean,
    onCheckListClicked: () -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    isGridLayout: Boolean,
) {

    when {
        isGridLayout -> {
            LazyVerticalStaggeredGrid(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                columns = StaggeredGridCells.Adaptive(180.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                contentPadding = PaddingValues(LARGE_PADDING), // TODO { paddings }

                content = {

                    items(
                        taskTableList.size,
                        key = { task -> task },
                    ) { index ->

                        Box(
                            modifier = modifier
                                .clip(HOME_SCREEN_ROUNDED_CORNERS)
                                .fillMaxWidth(0.9f)   // TODO { hardcode value }
                                .background(Color.Transparent)
                                .padding(EXTRA_SMALL_PADDING),
                            content = {
                                TaskItem(
                                    taskTable = taskTableList[index],
                                    navigateToTaskScreen = navigateToTaskScreen,
                                    isGridLayout = isGridLayout,
                                    onCheckedChange = { onCheckListClicked() }

                                )
                            }
                        )
                    }
                }
            )
        }

        else -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                contentPadding = PaddingValues(LARGE_PADDING), // TODO { paddings }

                content = {
                    items(items = taskTableList, key = { task -> task.taskId }) { task ->

                        Box(
                            modifier = modifier
                                .clip(HOME_SCREEN_ROUNDED_CORNERS)
                                .fillMaxWidth(0.9f)   // TODO { hardcode value }
                                .background(Color.Transparent)
                                .padding(EXTRA_SMALL_PADDING)
                                .animateContentSize(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                ),
                            content = {
                                TaskItem(
                                    taskTable = task,
                                    navigateToTaskScreen = navigateToTaskScreen,
                                    isGridLayout = isGridLayout,
                                    onCheckedChange = { onCheckListClicked() },

                                )
                            }
                        )
                    }
                }
            )
        }
    }
}

