package com.alexandros.p.gialamas.duetodo.ui.screens

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.components.fabbutton.FabButton
import com.alexandros.p.gialamas.duetodo.ui.components.tasks.DisplayTasks
import com.alexandros.p.gialamas.duetodo.ui.components.tasks.TaskList
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrContentColor
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.TopBarOrSearchBar
import com.alexandros.p.gialamas.duetodo.util.SearchBarState


//var previewTopBar : Boolean = true

@Composable
fun HomeScreen(
    navController: NavHostController,
    context: Context,
    navigateToTask: (taskId: Int) -> Unit,
    taskViewModel: TaskViewModel
) {

    LaunchedEffect(key1 = true) {
        taskViewModel.getAllTasks()
    }

    val allTasks by taskViewModel.allTasks.collectAsState()
    val searchBarState : SearchBarState by taskViewModel.searchBarState
    val searchTextState : String by taskViewModel.searchTextState

    MaterialTheme(
        shapes = shapes.copy(RoundedCornerShape(16.dp)),
        content = {
            Scaffold(
                containerColor = colorScheme.topAppBarrBackgroundColor,
                contentColor = colorScheme.topAppBarrContentColor,
                topBar = { TopBarOrSearchBar(
                    taskViewModel = taskViewModel,
                    searchBarState = searchBarState,
                    searchTextState = searchTextState
                ) },
                floatingActionButton = { FabButton(onFabClicked = {}) },
                bottomBar = {},
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    MaterialTheme (
                        content = {
                    Surface(
                        modifier = Modifier
                            .padding(it)
                            .border(BorderStroke(
                                color = Color.Gray,
                                width = 6.dp,
                                ),
                                shape = RoundedCornerShape(40.dp)
                            )
                        .border(BorderStroke(
                        color = Color.LightGray,
                        width = 2.dp,
                    ), shape = RoundedCornerShape(40.dp),
                        ),
                        color = colorScheme.topAppBarrBackgroundColor,
                        shape = RoundedCornerShape(40.dp),
                        content = {
                            DisplayTasks(
                                taskTableList = allTasks,
                                navigateToTaskScreen = navigateToTask
                                )
                        })
                }
                    )
                }
            )
        }
    )
}

//@Composable
//@Preview
//private fun HomeScreenPreview() {
//
//previewTopBar = true
//
//    HomeScreen(
//        navigateToTask = {},
//        navController = NavHostController(LocalContext.current),
//        context = LocalContext.current,
//        taskViewModel = hiltViewModel()
//    )
//}

