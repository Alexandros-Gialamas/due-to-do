package com.alexandros.p.gialamas.duetodo.navigation

import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.util.Action


object Destination : Screens {
    override fun home(navController: NavHostController, action: Action) {
        navController.navigate(Route.Home.createRoute(action)) {
            popUpTo(Route.Home.route) { inclusive = true }
        }
    }

    override fun task(navController: NavHostController, action: Action) {
        navController.navigate(Route.Task.createRoute(action))
    }
}