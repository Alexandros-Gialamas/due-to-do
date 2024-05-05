package com.alexandros.p.gialamas.duetodo.navigation

import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.util.Action

interface Destinations {

    fun homeScreen(navController: NavHostController) : (Action) -> Unit

    fun taskScreen(navController: NavHostController) : (Int) -> Unit

}
