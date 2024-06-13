package com.alexandros.p.gialamas.duetodo.navigation

import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction

interface Destinations {

    fun homeScreen(navController: NavHostController) : (DatabaseAction) -> Unit

    fun taskScreen(navController: NavHostController) : (Int) -> Unit

}
