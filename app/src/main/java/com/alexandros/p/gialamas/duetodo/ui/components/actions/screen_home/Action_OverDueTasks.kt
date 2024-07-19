package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskTable
import com.alexandros.p.gialamas.duetodo.ui.theme.myActivatedColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.util.RequestState

@Composable
fun ActionOverDueTasks(
    modifier: Modifier = Modifier,
    onShowOverdueTasksClicked: () -> Unit,
    showOverdueTasksState: Boolean,
    areOverdueTasksState: Boolean,
    myActivatedColor: Color = MaterialTheme.colorScheme.myActivatedColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
) {

    Box(
        modifier = modifier
            .wrapContentSize(),
        content = {

            if (areOverdueTasksState){
                Box(
                    modifier = modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-5).dp, y = (5).dp)
                        .size(8.dp)
                        .background(color = Color.Red, shape = CircleShape)
                )
            }

            IconButton(
                onClick = onShowOverdueTasksClicked,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_repeat),
                    contentDescription = stringResource(id = R.string.BottomBar_Action_OverDueTasks_Icon_Description),
                    tint = if (showOverdueTasksState) myActivatedColor else myContentColor
                )
            }

        }
    )


}