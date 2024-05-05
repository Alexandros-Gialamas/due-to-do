package com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen

import androidx.compose.runtime.Composable
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.util.Action

@Composable
 fun EditOrNewTopBar(
    selectedTask : TaskTable?,
    navigateToHomeScreen : (Action) -> Unit,
){
    if (selectedTask == null){
        NewTaskTopBar(navigateToHomeScreen = navigateToHomeScreen)
    }else{
        EditTaskTopBar(selectedTask = selectedTask, navigateToHomeScreen = navigateToHomeScreen)
    }
}