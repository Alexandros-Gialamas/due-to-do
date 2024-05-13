package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.util.CrudAction
import com.alexandros.p.gialamas.duetodo.util.Constants.KEYBOARD_DELAY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ActionInsert(
    onAddClicked: (CrudAction) -> Unit,
    scope : CoroutineScope,
    keyboardController : SoftwareKeyboardController?,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    IconButton(
        onClick = {
            scope.launch {
                keyboardController?.hide()
                delay(KEYBOARD_DELAY)
                onAddClicked(CrudAction.INSERT)
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