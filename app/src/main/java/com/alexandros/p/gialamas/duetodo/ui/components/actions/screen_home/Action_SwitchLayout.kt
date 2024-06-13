package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun ActionSwitchLayout(
    onLayoutClicked: () -> Unit,
    isGridLayout : Boolean,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor
) {

    val columnView = painterResource(id = R.drawable.ic_column_view)
    val gridView = painterResource(id = R.drawable.ic_grid_view)

    IconButton(
        onClick = { onLayoutClicked() },
    ) {
        Icon(
            painter = if (isGridLayout) columnView else gridView,
            contentDescription = stringResource(id = R.string.BottomBar_Action_Layout_Icon_Description),
            tint = myContentColor
        )
    }
}

@Composable
@Preview
private fun ActionSwitchLayoutPreview() {
    ActionSwitchLayout(
        onLayoutClicked = {},
        isGridLayout = true,
        myContentColor = MaterialTheme.colorScheme.myContentColor
    )
}