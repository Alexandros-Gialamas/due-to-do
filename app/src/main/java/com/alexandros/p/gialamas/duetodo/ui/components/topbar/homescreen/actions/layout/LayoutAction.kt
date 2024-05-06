package com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme

@Composable
fun LayoutAction(
    onLayoutClicked : () -> Unit,
){
    IconButton(
        onClick = { onLayoutClicked() },
    ) {
        Icon(imageVector = Icons.Filled.Lock,
            contentDescription = stringResource(id = R.string.App_Bar_Layout_Icon_Description),
            tint = MyTheme.MyCloud
        )
    }
}

@Composable
@Preview
private fun LayoutActionPreview() {
    LayoutAction(
        onLayoutClicked = {},
    )
}