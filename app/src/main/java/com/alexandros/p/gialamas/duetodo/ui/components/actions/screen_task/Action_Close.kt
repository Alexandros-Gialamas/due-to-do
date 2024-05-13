package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.util.CrudAction

@Composable
fun ActionClose(
    onCloseClicked : (CrudAction) -> Unit,
    myBackgroundColor : Color,
    myContentColor : Color,
    myTextColor : Color
){
    IconButton(onClick = { onCloseClicked(CrudAction.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.Close_Task_Icon_Description),
            tint = myContentColor)
    }
}