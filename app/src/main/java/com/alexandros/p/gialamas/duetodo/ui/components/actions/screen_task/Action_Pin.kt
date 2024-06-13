package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.myActivatedColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.util.Constants.ICON_NO_ROTATION
import com.alexandros.p.gialamas.duetodo.util.Constants.ICON_ROTATION

@Composable
fun ActionPin(
    modifier: Modifier = Modifier,
    onPinClicked: () -> Unit,
    pin: Boolean,
    myActivatedColor: Color = MaterialTheme.colorScheme.myActivatedColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor
){
    IconButton(
        modifier = modifier
            .rotate(degrees = if (pin) ICON_ROTATION else ICON_NO_ROTATION),
        onClick = { onPinClicked() }
    ) {
        Icon(
            painter =
            if (pin) {
                painterResource(id = R.drawable.ic_pinned)
            } else {
                painterResource(id = R.drawable.ic_pin)
            },
            contentDescription = stringResource(id = R.string.Pinned_Task_Icon_Description),
            tint = if (pin) myActivatedColor else myContentColor
        )
    }
}