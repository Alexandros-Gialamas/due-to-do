package com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme
import com.alexandros.p.gialamas.duetodo.util.Action

@Composable
 fun BackAction(
    onBackClicked : (Action) -> Unit
){
     IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {  // TODO { maybe Save Action }
            Icon(imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.Go_Back_Icon_Description),
                tint = MyTheme.MyCloud)
     }
}