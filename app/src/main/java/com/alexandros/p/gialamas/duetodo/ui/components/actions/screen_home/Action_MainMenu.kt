package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun ActionMainMenu(
    onMenuClicked: () -> Unit,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
) {
    IconButton(
        onClick = { onMenuClicked() },
    ) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = stringResource(id = R.string.SearchBar_Action_Menu_Icon_Description),
            tint = myContentColor
        )
    }
}

@Composable
@Preview
private fun ActionMainMenuPreview() {
    ActionMainMenu(
        onMenuClicked = {},
        myContentColor = MaterialTheme.colorScheme.myContentColor,
    )
}