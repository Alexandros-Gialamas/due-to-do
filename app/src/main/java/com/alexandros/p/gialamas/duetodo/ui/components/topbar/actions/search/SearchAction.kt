package com.alexandros.p.gialamas.duetodo.ui.components.topbar.actions.search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme

@Composable
fun SearchAction(
    onSearchClicked : () -> Unit,
){

    MaterialTheme (
        content = {
            IconButton(
                onClick = { onSearchClicked() },
            ) {
                Icon(imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.App_Bar_Search_Description),
                    tint = MyTheme.MyCloud
                )
            }
        }
    )
}

@Composable
@Preview
private fun SearchActionPreview() {
    SearchAction(
            onSearchClicked = {},
        )
}