package com.alexandros.p.gialamas.duetodo.ui.components.tasks.homescreen


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_SMALL_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListItem(
    modifier: Modifier = Modifier,
    taskViewModel : TaskViewModel = hiltViewModel(),   // TODO { find better way }
    taskTableList: List<TaskTable> = listOf(),
    onSwipeToDelete: (DatabaseAction, TaskTable) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    isGridLayout : Boolean,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
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
                    ) {  index ->

                        var itemAppear by remember { mutableStateOf(false) }

                        LaunchedEffect(key1 = true) {
                            itemAppear = true
                        }

                        AnimatedVisibility(
                            visible = itemAppear && isGridLayout,
                            enter = expandVertically(
                                animationSpec = tween(400, easing = EaseInOutQuart)
                            ),
                            exit = shrinkVertically(
                                animationSpec = tween(400, easing = EaseInOutQuart)
                            )

                        ) {
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
                                        isGridLayout = isGridLayout
                                    )
                                }
                            )
                        }
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

                        val dismissState = rememberDismissState()
                        val dismissDirection = dismissState.dismissDirection
                        val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
                        if (isDismissed && dismissDirection == DismissDirection.EndToStart) {

                            taskViewModel.viewModelScope.launch { // TODO { remove scope from here }
                                delay(400)
                            }
                            onSwipeToDelete(DatabaseAction.DELETE, task)
                        }
                        val degrees by animateFloatAsState(
                            targetValue =
                            if (dismissState.targetValue == DismissValue.Default)
                                0f
                            else
                                -45f,
                            label = stringResource(id = R.string.Swipe_to_Delete_description_label)
                        )

                        var itemAppear by remember { mutableStateOf(false) }

                        LaunchedEffect(key1 = true) {
                            itemAppear = true
                        }

                        AnimatedVisibility(
                            visible = itemAppear && !isDismissed,
                            enter = expandVertically(
                                animationSpec = tween(400, easing = EaseInOutQuart)
                            ),
                            exit = shrinkVertically(
                                animationSpec = tween(400, easing = EaseInOutQuart)
                            )

                        ) {

                            SwipeToDismiss(
                                state = dismissState,
                                directions = setOf(DismissDirection.EndToStart),
                                background = {
                                    SwipeBackground(
                                        degrees = degrees,
                                        backgroundColor = Color.Transparent,
                                        iconColor = myContentColor
                                    )
                                }, // TODO { revisit color }
                                dismissContent = {
                                    Box(
                                        modifier = modifier
                                            .clip(HOME_SCREEN_ROUNDED_CORNERS)
                                            .fillMaxWidth(0.9f)   // TODO { hardcode value }
                                            .background(Color.Transparent)
                                            .padding(EXTRA_SMALL_PADDING),
                                        content = {
                                            TaskItem(
                                                taskTable = task,
                                                navigateToTaskScreen = navigateToTaskScreen,
                                                isGridLayout = isGridLayout
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
            )
        }
    }


}

@Composable
fun SwipeBackground(
    modifier: Modifier = Modifier,
    degrees: Float,
    backgroundColor: Color,
    iconColor: Color
) { // TODO { revisit color }
    Box(
        modifier = modifier
            .clip((HOME_SCREEN_ROUNDED_CORNERS))
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = EXTRA_SMALL_PADDING),
        contentAlignment = Alignment.CenterEnd,
        content = {
            Icon(
                modifier = modifier
                    .rotate(degrees = degrees),
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(id = R.string.Delete_Task_Icon_Description),
                tint = iconColor
            )
        }
    )
}