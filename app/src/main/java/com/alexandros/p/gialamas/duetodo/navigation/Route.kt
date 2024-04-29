package com.alexandros.p.gialamas.duetodo.navigation

import com.alexandros.p.gialamas.duetodo.util.Action
import com.alexandros.p.gialamas.duetodo.util.Constants.HOME_SCREEN
import com.alexandros.p.gialamas.duetodo.util.Constants.TASK_SCREEN

sealed class Route(val route : String) {
    object Home : Route("$HOME_SCREEN/{action}") //TODO { check string again with Constants }
    object Task : Route("$TASK_SCREEN/{action}")

    fun createRoute(action : Action) : String = route.replace("{action}" , action.name)

}