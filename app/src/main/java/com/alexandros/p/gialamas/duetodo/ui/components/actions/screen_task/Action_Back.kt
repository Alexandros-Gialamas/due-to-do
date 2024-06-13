package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.util.DatabaseAction

@Composable
 fun ActionBack(
    onBackClicked : (DatabaseAction) -> Unit,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor
){
     IconButton(onClick = { onBackClicked(DatabaseAction.NO_ACTION) }) {  // TODO { maybe Save Action }
            Icon(imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.Go_Back_Icon_Description),
                tint = myContentColor)
     }
}