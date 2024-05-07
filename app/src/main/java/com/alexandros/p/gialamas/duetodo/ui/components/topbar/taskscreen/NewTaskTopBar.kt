package com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions.AddAction
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.taskscreen.actions.BackAction
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.util.Action

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskTopBar(
    navigateToHomeScreen: (Action) -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    val editedBackgroundColor = myBackgroundColor.copy(alpha = 0.7f)
    val mySecondBackgroundColor = Brush.myBackgroundBrush(radius = 6800f / 1.1f)

    TopAppBar(
        modifier = Modifier
            .background(mySecondBackgroundColor),
        navigationIcon = {
            BackAction(
                onBackClicked = navigateToHomeScreen,
                myBackgroundColor = myBackgroundColor,
                myContentColor = myContentColor,
                myTextColor = myTextColor
            )
        },
        title = {
            Text(
                text = stringResource(id = R.string.New_Task_Top_Bar_Title),
                color = myTextColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(editedBackgroundColor),
        actions = {
            AddAction(
                onAddClicked = navigateToHomeScreen,
                myBackgroundColor = myBackgroundColor,
                myContentColor = myContentColor,
                myTextColor = myTextColor
            )
        }
    )
}


@Composable
@Preview
private fun NewTaskTopBarPreview() {
    NewTaskTopBar(
        navigateToHomeScreen = {},
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        myTextColor = MaterialTheme.colorScheme.myTextColor
    )
}