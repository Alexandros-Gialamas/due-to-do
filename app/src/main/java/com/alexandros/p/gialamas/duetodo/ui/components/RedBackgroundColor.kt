package com.alexandros.p.gialamas.duetodo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material.Icon
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.EXTRA_SMALL_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme
import com.alexandros.p.gialamas.duetodo.ui.theme.TaskPriorityColor_High

@Composable
fun RedBackGround(degrees : Float) { // TODO { revisit color }

        Box(
            modifier = Modifier
                .clip((HOME_SCREEN_ROUNDED_CORNERS))
                .fillMaxSize()
                .background(TaskPriorityColor_High)
                .padding(horizontal = EXTRA_SMALL_PADDING),
            contentAlignment = Alignment.CenterEnd,
            content = {
                Icon(
                    modifier = Modifier
                        .rotate(degrees = degrees),
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.Delete_Task_Icon_Description),
                    tint = MyTheme.MyCloud
                )
            }
        )

}