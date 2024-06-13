package com.alexandros.p.gialamas.duetodo.navigation

import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction
import com.alexandros.p.gialamas.duetodo.util.Constants.HOME_SCREEN


object Route : Destinations {
    override fun homeScreen(navController: NavHostController): (DatabaseAction) -> Unit = { action ->
        navController.navigate(
            "home/${action.name}"
        ) {
            popUpTo(HOME_SCREEN) { inclusive = true }
        }
    }

    override fun taskScreen(navController: NavHostController): (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}
