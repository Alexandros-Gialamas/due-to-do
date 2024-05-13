package com.alexandros.p.gialamas.duetodo.navigation

import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.util.CrudAction

interface Destinations {

    fun homeScreen(navController: NavHostController) : (CrudAction) -> Unit

    fun taskScreen(navController: NavHostController) : (Int) -> Unit

}
