package com.alexandros.p.gialamas.duetodo.ui.components.topbar.homescreen.actions.menu

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme

@Composable
fun VerticalMenuAction(
    onMenuItemClicked: () -> Unit,
    onDeleteAllTasksClicked : () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    MaterialTheme (
        content = {
            IconButton(
                onClick = { expanded = !expanded },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_vertical_menu),
                    contentDescription = stringResource(id = R.string.App_Bar_Delete_All_Description),
                    tint = MyTheme.MyCloud  // TODO { maybe need theme color }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    content = {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    modifier = Modifier
                                        .padding(start = LARGE_PADDING),
                                    text = stringResource(id = R.string.App_Bar_Delete_All_Text),
                                    style = typography.titleMedium
                                )
                            },
                            onClick = {
                                expanded = false
                                onMenuItemClicked()
                                onDeleteAllTasksClicked()
                            })

                    })
            }
        }
    )

}

@Composable
@Preview
private fun VerticalMenuActionPreview() {
    VerticalMenuAction(
        onMenuItemClicked = {},
        onDeleteAllTasksClicked = {}
    )
}