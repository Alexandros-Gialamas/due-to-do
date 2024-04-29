package com.alexandros.p.gialamas.duetodo.navigation

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alexandros.p.gialamas.duetodo.ui.screens.HomeScreen
import com.alexandros.p.gialamas.duetodo.ui.viewmodels.TaskViewModel
import com.alexandros.p.gialamas.duetodo.util.Constants.HOME_SCREEN_ARGUMENT_KEY
import com.alexandros.p.gialamas.duetodo.util.Constants.TASK_SCREEN_ARGUMENT_KEY

@Composable
fun NavigationComposable(context: Context, navController: NavHostController, taskViewModel : TaskViewModel) {



    val homeArguments = listOf(navArgument(HOME_SCREEN_ARGUMENT_KEY) {
        type = NavType.StringType
    })
    val taskArguments = listOf(navArgument(TASK_SCREEN_ARGUMENT_KEY) {
        type = NavType.IntType
    })

    NavHost(navController = navController, startDestination = Route.Home.route) {
        composable(Route.Home.route) {
            HomeScreen(
                navController,
                context,
                taskViewModel = taskViewModel,
                navigateToTask = {} // TODO { argument }

            )

            //  Destination.home(navController,Action.INSERT)
        }
//        composable(Route.Task.route) {
//            TaskScreen(
//                navController,
//                context,
//            )
//        }
    }
}