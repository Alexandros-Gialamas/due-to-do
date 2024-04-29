package com.alexandros.p.gialamas.duetodo.navigation

import androidx.navigation.NavHostController
import com.alexandros.p.gialamas.duetodo.util.Action

interface Screens {
    fun home(navController: NavHostController, action: Action) : Unit
    fun task(navController: NavHostController, action: Action) : Unit
}
