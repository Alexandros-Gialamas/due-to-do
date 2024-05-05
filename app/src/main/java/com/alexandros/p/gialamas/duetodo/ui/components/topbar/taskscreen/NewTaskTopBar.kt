package com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions.AddAction
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions.BackAction
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrContentColor
import com.alexandros.p.gialamas.duetodo.util.Action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskTopBar(
    navigateToHomeScreen : (Action) -> Unit,
) {
    TopAppBar(
        navigationIcon = { BackAction(onBackClicked = navigateToHomeScreen)},
        title = {
            Text(
                text = stringResource(id = R.string.New_Task_Top_Bar_Title),
                color = MaterialTheme.colorScheme.topAppBarrContentColor
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(MaterialTheme.colorScheme.topAppBarrBackgroundColor),
        actions = {
                AddAction(onAddClicked = navigateToHomeScreen)
        }

    )
}


@Composable
@Preview
private fun NewTaskTopBarPreview() {
    NewTaskTopBar(
        navigateToHomeScreen = {},

    )
}