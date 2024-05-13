package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alexandros.p.gialamas.duetodo.R
import com.alexandros.p.gialamas.duetodo.data.models.TaskCategoryTable
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush

@Composable
fun ActionTaskCategorySelect(
    taskCategories: List<TaskCategoryTable>,
    onCategoryClicked: (category: String) -> Unit,
    myBackgroundColor: Color,
    myContentColor: Color,
    myTextColor: Color
) {

    var expanded by remember { mutableStateOf(false) }

    val dropDownBackgroundColor = Brush.myBackgroundBrush(radius = 900 / 1f)

    IconButton(
        onClick = { expanded = !expanded },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_select_category),
            contentDescription = stringResource(id = R.string.BottomBar_Action_Sort_Description),
            tint = myContentColor
        )

        DropdownMenu(
            modifier = Modifier
                .clip(HOME_SCREEN_ROUNDED_CORNERS)
                .background(dropDownBackgroundColor)
                .border(
                    width = FIRST_BORDER_STROKE,
                    color = myContentColor.copy(alpha = LIGHT_BORDER_STROKE_ALPHA),
                    shape = HOME_SCREEN_ROUNDED_CORNERS
                )
                .border(
                    width = SECOND_BORDER_STROKE,
                    color = myBackgroundColor,
                    shape = HOME_SCREEN_ROUNDED_CORNERS
                ),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            content = {

                DropdownMenuItem(
                    text = {
                        Text(
                            modifier = Modifier
                                .padding(start = LARGE_PADDING),
                            text = "None",
                            style = MaterialTheme.typography.bodyMedium,
                            color = myTextColor // TODO { revisit }
                        )
                    },
                    onClick = {
                        expanded = false
//                        onCategoryClicked()
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(
                            modifier = Modifier
                                .padding(start = LARGE_PADDING),
                            text = "Reminders",
                            style = MaterialTheme.typography.bodyMedium,
                            color = myTextColor // TODO { revisit }
                        )
                    },
                    onClick = {
                        expanded = false
//                        onCategoryClicked(taskCategory)
                    }
                )


                taskCategories.forEach { taskCategory ->

                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier
                                    .padding(start = LARGE_PADDING),
                                text = taskCategory.categoryName,
                                style = MaterialTheme.typography.bodyMedium,
                                color = myTextColor // TODO { revisit }
                            )
                        },
                        onClick = {
                            expanded = false
                            onCategoryClicked(taskCategory.categoryName)
                        }
                    )
                }
            }
        )
    }
}