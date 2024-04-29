package com.alexandros.p.gialamas.duetodo.ui.components.topbar.actions.sort

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.theme.MyTheme

@Composable
fun SortAction(
    onSortClicked: (taskPriority : TaskPriority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    MaterialTheme (
        content = {
            IconButton(
                onClick = { expanded = !expanded },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter_tasks),
                    contentDescription = stringResource(id = R.string.App_Bar_Sort_Description),
                    tint = MyTheme.MyCloud
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    content = {
                        DropdownMenuItem(
                            text = { TaskPriorityItem(taskPriority = TaskPriority.NONE) },
                            onClick = {
                                expanded = false
                                onSortClicked(TaskPriority.NONE)
                            })
                        DropdownMenuItem(
                            text = { TaskPriorityItem(taskPriority = TaskPriority.LOW) },
                            onClick = {
                                expanded = false
                                onSortClicked(TaskPriority.LOW)
                            })
                        DropdownMenuItem(
                            text = { TaskPriorityItem(taskPriority = TaskPriority.MEDIUM) },
                            onClick = {
                                expanded = false
                                onSortClicked(TaskPriority.MEDIUM)
                            })
                        DropdownMenuItem(
                            text = { TaskPriorityItem(taskPriority = TaskPriority.HIGH) },
                            onClick = {
                                expanded = false
                                onSortClicked(TaskPriority.HIGH)
                            })
                    }
                )
            }
        }
    )


}

@Composable
@Preview
private fun SortActionPreview() {
    SortAction(
        onSortClicked = {},
    )
}