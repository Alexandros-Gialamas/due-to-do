package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.util.Constants.ICON_NO_ROTATION
import com.alexandros.p.gialamas.duetodo.util.Constants.ICON_ROTATION
import com.alexandros.p.gialamas.duetodo.util.SettingAction

@Composable
fun ActionPin(
    onPinClicked: (settingAction: SettingAction) -> Unit,
    pin: Boolean,
    myActivatedColor: Color,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
){
    IconButton(
        modifier = Modifier
            .rotate(degrees = if (pin) ICON_ROTATION else ICON_NO_ROTATION),
        onClick = {
            onPinClicked(
                if (pin) SettingAction.DISCARD else SettingAction.SET
            )
        }
    ) {
        Icon(
            painter =
            if (pin) {
                painterResource(id = R.drawable.ic_pinned)
            } else {
                painterResource(id = R.drawable.ic_pin)
            },
            contentDescription = stringResource(id = R.string.Reminder_Task_Icon_Description),
            tint = if (pin) myActivatedColor else myContentColor
        )
    }
}