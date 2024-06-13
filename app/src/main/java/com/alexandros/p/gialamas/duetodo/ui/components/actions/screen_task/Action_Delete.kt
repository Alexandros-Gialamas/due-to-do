package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor

@Composable
fun ActionDelete(
    onDeleteClicked : () -> Unit,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor
){
    IconButton(onClick = { onDeleteClicked() }) {
        Icon(imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.Delete_Task_Icon_Description),
            tint = myContentColor)
    }
}