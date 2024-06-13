package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.myActivatedColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor

@Composable
fun ActionOverDueTasks(
    onOverDueClicked: () -> Unit,
    overDued : Boolean,
    myActivatedColor: Color = MaterialTheme.colorScheme.myActivatedColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
) {
    IconButton(
        onClick = { onOverDueClicked() },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_repeat),
            contentDescription = stringResource(id = R.string.BottomBar_Action_OverDueTasks_Icon_Description),
            tint = if(overDued) myActivatedColor else myContentColor
        )
    }
}