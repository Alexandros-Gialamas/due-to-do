package com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
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
fun LayoutAction(
    onLayoutClicked: () -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    IconButton(
        onClick = { onLayoutClicked() },
    ) {
        Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = stringResource(id = R.string.App_Bar_Layout_Icon_Description),
            tint = myContentColor
        )
    }
}

@Composable
@Preview
private fun LayoutActionPreview() {
    LayoutAction(
        onLayoutClicked = {},
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        myTextColor = MaterialTheme.colorScheme.myTextColor
    )
}