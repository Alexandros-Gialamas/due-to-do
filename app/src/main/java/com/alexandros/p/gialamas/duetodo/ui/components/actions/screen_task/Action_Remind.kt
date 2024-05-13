package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.util.SettingAction

@Composable
fun ActionReminder(
    onRemindClicked: (settingAction: SettingAction) -> Unit,
    dueDate: Long?,
    myActivatedColor: Color,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    IconButton(
        onClick = {
            onRemindClicked(
                if (dueDate != null) SettingAction.EDIT else SettingAction.SET
            )
        }
    ) {
        Icon(
            painter =
            if (dueDate != null) {
                painterResource(id = R.drawable.ic_notification_added)
            } else {
                painterResource(id = R.drawable.ic_add_notification)
            },
            contentDescription = stringResource(id = R.string.Reminder_Task_Icon_Description),
            tint = if (dueDate != null) myActivatedColor else myContentColor
        )
    }
}