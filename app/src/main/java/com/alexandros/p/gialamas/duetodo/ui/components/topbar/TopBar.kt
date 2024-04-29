package com.alexandros.p.gialamas.duetodo.ui.components.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.components.topbar.actions.Actions
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.topAppBarrContentColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun TopBar(onSearchClicked : () -> Unit,) {
        MaterialTheme (
            content = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.App_Bar_Title_Description),
                            color = colorScheme.topAppBarrContentColor)
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(colorScheme.topAppBarrBackgroundColor),
                    actions = { Actions(
                        onSearchClicked = { onSearchClicked() },
                        onSortClicked = {},
                        onMenuItemClicked = {}
                    )
                    }
                )
            }
        )
    }

@Composable
@Preview
private fun TopBarPreview() {
    TopBar(
        onSearchClicked = {},
    )
}