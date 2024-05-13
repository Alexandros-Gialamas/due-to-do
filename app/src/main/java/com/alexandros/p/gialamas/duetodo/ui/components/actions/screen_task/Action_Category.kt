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
fun ActionCategory(
    onCategoryClicked: (settingAction: SettingAction) -> Unit,
    category: String,
    myActivatedColor: Color,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    IconButton(
        onClick = {
            onCategoryClicked(
                if (category.isNullOrBlank()) SettingAction.SET else SettingAction.EDIT
            )
        }
    ) {
        Icon(
            painter =
            if (category.isNullOrBlank()) {
                painterResource(id = R.drawable.ic_label)
            } else {
                painterResource(id = R.drawable.ic_label_added)
            },
            contentDescription = stringResource(id = R.string.Reminder_Task_Icon_Description),
            tint = if (category.isNullOrBlank()) myContentColor else myActivatedColor
        )
    }
}