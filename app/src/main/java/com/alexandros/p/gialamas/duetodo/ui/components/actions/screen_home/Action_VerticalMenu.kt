package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor

@Composable
fun ActionVerticalMenu(
    onDeleteAllTasksClicked: () -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = !expanded },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = stringResource(id = R.string.VerticalMenu_Action_Delete_All_Description),
            tint = myContentColor
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
                            text = stringResource(id = R.string.VerticalMenu_Action_Delete_All_Text),
                            style = typography.titleMedium
                        )
                    },
                    onClick = {
                        expanded = false
                        onDeleteAllTasksClicked()
                    }
                )
            }
        )
    }
}

@Composable
@Preview
private fun ActionVerticalMenuPreview() {
    ActionVerticalMenu(
        onDeleteAllTasksClicked = {},
        myBackgroundColor = MaterialTheme.colorScheme.myBackgroundColor,
        myContentColor = MaterialTheme.colorScheme.myContentColor,
        myTextColor = MaterialTheme.colorScheme.myTextColor
    )
}