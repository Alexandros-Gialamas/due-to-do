package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction
import com.alexandros.p.gialamas.duetodo.util.Constants.KEYBOARD_DELAY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ActionInsert(
    onAddClicked: (DatabaseAction) -> Unit,
    scope : CoroutineScope,
    keyboardController : SoftwareKeyboardController?,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor
) {

    IconButton(
        onClick = {
            scope.launch {
                keyboardController?.hide()
                delay(KEYBOARD_DELAY)
                onAddClicked(DatabaseAction.INSERT)
            }
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.Add_Task_Icon_Description),
            tint = myContentColor
        )
    }
}