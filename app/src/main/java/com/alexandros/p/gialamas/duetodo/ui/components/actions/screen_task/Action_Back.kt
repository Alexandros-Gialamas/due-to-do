package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.util.CrudAction

@Composable
 fun ActionBack(
    onBackClicked : (CrudAction) -> Unit,
    myBackgroundColor : Color,
    myContentColor : Color,
    myTextColor : Color
){
     IconButton(onClick = { onBackClicked(CrudAction.NO_ACTION) }) {  // TODO { maybe Save Action }
            Icon(imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.Go_Back_Icon_Description),
                tint = myContentColor)
     }
}