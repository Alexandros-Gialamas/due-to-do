package com.alexandros.p.gialamas.duetodo.navigation

import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.alexandros.p.gialamas.duetodo.ui.screens.HomeScreen
import com.alexandros.p.gialamas.duetodo.ui.screens.TaskScreen
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.Action
import com.alexandros.p.gialamas.duetodo.util.Constants.HOME_SCREEN
import com.alexandros.p.gialamas.duetodo.util.Constants.HOME_SCREEN_ARGUMENT_KEY
import com.alexandros.p.gialamas.duetodo.util.Constants.TASK_SCREEN
import com.alexandros.p.gialamas.duetodo.util.Constants.TASK_SCREEN_ARGUMENT_KEY
import com.alexandros.p.gialamas.duetodo.util.toAction

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationComposable(
    context: Context,
    navController: NavHostController,
    taskViewModel : TaskViewModel
) {

//    SharedTransitionLayout {

        val screen = remember(navController) { Route }

        val homeArguments = listOf(navArgument(HOME_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
        })
        val taskArguments = listOf(navArgument(TASK_SCREEN_ARGUMENT_KEY) {
            type = NavType.IntType
        })

        NavHost(navController = navController, startDestination = HOME_SCREEN) {

            composable(route = HOME_SCREEN, arguments = homeArguments) {navBackStackEntry ->

                val action = navBackStackEntry?.arguments?.getString(HOME_SCREEN_ARGUMENT_KEY).toAction()

                var myAction by rememberSaveable { mutableStateOf(Action.NO_ACTION) }

                LaunchedEffect(key1 = myAction) {
                    if (action != myAction){
                        myAction = action
                        taskViewModel.updateAction(newAction = action)
                    }
                }

                val databaseAction by taskViewModel.action

                HomeScreen(
                    action = databaseAction,
                    navController,
                    context,
                    taskViewModel = taskViewModel,
                    navigateToTaskScreen = screen.taskScreen(navController)
                )
            }

            composable(route = TASK_SCREEN, arguments = taskArguments) { navBackStackEntry ->
                val taskId = navBackStackEntry.arguments?.getInt(TASK_SCREEN_ARGUMENT_KEY)

                LaunchedEffect(key1 = taskId) {
                    taskViewModel.getSelectedTask(taskId = taskId ?: -1)
                }

                val selectedTask by taskViewModel.selectedTask.collectAsState()

                LaunchedEffect(key1 = selectedTask) {
                    if (selectedTask != null || taskId == -1){
                        taskViewModel.updateDisplayTaskFields(selectedTask = selectedTask)
                    }
                }


                TaskScreen(
                    selectedTask = selectedTask,
                    taskViewModel = taskViewModel,
                    navigateToHomeScreen = screen.homeScreen(navController),
                    context = context
                )
            }
        }
//    }
}