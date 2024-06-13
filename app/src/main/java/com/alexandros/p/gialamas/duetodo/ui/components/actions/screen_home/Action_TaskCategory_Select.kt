package com.alexandros.p.gialamas.duetodo.ui.components.actions.screen_home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import com.alexandros.p.gialamas.duetodo.data.models.TaskPriority
import com.alexandros.p.gialamas.duetodo.ui.theme.FIRST_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.HOME_SCREEN_ROUNDED_CORNERS
import com.alexandros.p.gialamas.duetodo.ui.theme.LARGE_PADDING
import com.alexandros.p.gialamas.duetodo.ui.theme.LIGHT_BORDER_STROKE_ALPHA
import com.alexandros.p.gialamas.duetodo.ui.theme.SECOND_BORDER_STROKE
import com.alexandros.p.gialamas.duetodo.ui.theme.myActivatedColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundBrush
import com.alexandros.p.gialamas.duetodo.ui.theme.myBackgroundColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myContentColor
import com.alexandros.p.gialamas.duetodo.ui.theme.myTextColor
import com.alexandros.p.gialamas.duetodo.util.Constants
import com.alexandros.p.gialamas.duetodo.util.DateSortOrder
import com.alexandros.p.gialamas.duetodo.util.RequestState
import com.alexandros.p.gialamas.duetodo.util.SelectedCategoryState

@Composable
fun ActionTaskCategorySelect(
    modifier: Modifier = Modifier,
    taskCategories: List<TaskCategoryTable>,
    categoryState: RequestState<String>,
    onCategoryClicked: (selectedCategory: String) -> Unit,
    myActivatedColor: Color = MaterialTheme.colorScheme.myActivatedColor,
    myBackgroundColor: Color = MaterialTheme.colorScheme.myBackgroundColor,
    myContentColor: Color = MaterialTheme.colorScheme.myContentColor,
    myTextColor: Color = MaterialTheme.colorScheme.myTextColor
) {

    var expanded by remember { mutableStateOf(false) }
    val noCategorySelected =
        (categoryState is RequestState.Success && categoryState.data == SelectedCategoryState.NONE.categoryName)
    val dropDownBackgroundColor = Brush.myBackgroundBrush(radius = 1200 / 1f)

    IconButton(
        onClick = { expanded = !expanded },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_label),
            contentDescription = stringResource(id = R.string.BottomBar_Action_Category_Selection_Description),
            tint = if (noCategorySelected) myContentColor else myActivatedColor
        )

        DropdownMenu(
            modifier = modifier
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
                val isNoneCategorySelected =
                    categoryState is RequestState.Success && categoryState.data == SelectedCategoryState.NONE.categoryName
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_label),
                            contentDescription = stringResource(id = R.string.BottomBar_Action_Category_Label_Icon_Description),
                            tint = myContentColor
                        )
                    },
                    text = {
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.None),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isNoneCategorySelected) myActivatedColor else myTextColor
                        )
                    },
                    onClick = {
                        expanded = false
                        onCategoryClicked(SelectedCategoryState.NONE.categoryName)
                    }
                )
                val isRemindersCategorySelected =
                    categoryState is RequestState.Success && categoryState.data == SelectedCategoryState.REMINDERS.categoryName
                DropdownMenuItem(
                    leadingIcon = {
                                  Icon(
                                      painter = painterResource(id = R.drawable.ic_label),
                                      contentDescription = stringResource(id = R.string.BottomBar_Action_Category_Label_Icon_Description),
                                      tint = myContentColor
                                  )
                    },
                    text = {
                        Text(
                            modifier = modifier,
                            text = stringResource(id = R.string.Reminders),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isRemindersCategorySelected) myActivatedColor else myTextColor
                        )
                    },
                    onClick = {
                        expanded = false
                        onCategoryClicked(SelectedCategoryState.REMINDERS.categoryName)
                    }
                )


                taskCategories.forEach { taskCategory ->
                    val isCustomCategorySelected =
                            categoryState is RequestState.Success && categoryState.data == taskCategory.categoryName
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_label),
                                contentDescription = stringResource(id = R.string.BottomBar_Action_Category_Label_Icon_Description),
                                tint = myContentColor
                            )
                        },
                        text = {
                            Text(
                                modifier = modifier,
                                text = taskCategory.categoryName,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isCustomCategorySelected) myActivatedColor else myTextColor
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