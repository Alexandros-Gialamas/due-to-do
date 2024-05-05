package com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme
import com.alexandros.p.gialamas.duetodo.util.Action

@Composable
fun AddAction(
    onAddClicked : (Action) -> Unit
){
    IconButton(onClick = { onAddClicked(Action.INSERT) }) {
        Icon(imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.Add_Task_Icon_Description),
            tint = MyTheme.MyCloud)
    }
}