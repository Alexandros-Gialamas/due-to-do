package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.util.CrudAction

@Composable
fun ActionUpdate(
    onUpdateClicked : (CrudAction) -> Unit,
    myBackgroundColor : Color,
    myContentColor : Color,
    myTextColor : Color
){
    IconButton(onClick = { onUpdateClicked(CrudAction.UPDATE) }) {
        Icon(imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.Update_Task_Icon_Description),
            tint = myContentColor)
    }
}