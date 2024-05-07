package com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.Actions
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.menu.MenuAction
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onSortClicked: (taskPriority: TaskPriority) -> Unit,
    onMenuItemClicked: () -> Unit,
    onMenuClicked: () -> Unit,
    onLayoutClicked: () -> Unit,
    onDeleteAllTasksClicked: () -> Unit,
    myBackgroundColor : Color,
    myContentColor : Color,
    myTextColor : Color
) {

    val editedBackgroundColor = myBackgroundColor.copy(alpha = 0.3f)

    TopAppBar(
        modifier = Modifier
            .background(editedBackgroundColor),
        title = { Text(text = "") },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        navigationIcon = {
            MenuAction(
                onMenuClicked = { /*TODO*/ },
                myBackgroundColor = myBackgroundColor,
                myContentColor = myContentColor,
                myTextColor = myTextColor
            )
        },
        actions = {
            Actions(
                onMenuClicked = onMenuClicked,
                onSortClicked = onSortClicked,
                onDeleteAllTasksClicked = onDeleteAllTasksClicked,
                onMenuItemClicked = onMenuItemClicked, // TODO { check this action }
                onLayoutClicked = onLayoutClicked,
                myBackgroundColor = myBackgroundColor,
                myContentColor = myContentColor,
                myTextColor = myTextColor
            )
        }
    )
}

@Composable
@Preview
private fun TopBarPreview() {
    TopBar(
        onSortClicked = {},
        onMenuItemClicked = {},
        onDeleteAllTasksClicked = {},
        onMenuClicked = {},
        onLayoutClicked = {},
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        myTextColor = MaterialTheme.colorScheme.myTextColor


    )
}